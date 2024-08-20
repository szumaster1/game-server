import core.api.consts.Items
import content.global.skill.gathering.farming.timers.CropGrowth
import core.ServerConstants
import core.api.log
import core.cache.Cache
import core.cache.crypto.ISAACCipher
import core.cache.crypto.ISAACPair
import core.game.interaction.ScriptProcessor
import core.game.node.Node
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.VarpManager
import core.game.node.entity.player.info.PlayerDetails
import core.game.node.entity.player.info.Rights
import core.game.node.entity.player.info.login.PlayerSaveParser
import core.game.node.entity.player.info.login.PlayerSaver
import core.game.node.entity.player.link.IronmanMode
import core.game.node.entity.player.link.SavedData
import core.game.node.entity.player.link.quest.QuestRepository
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItem
import core.game.node.item.Item
import core.game.shops.Shop
import core.game.shops.ShopItem
import core.game.system.config.ConfigParser
import core.game.system.config.ServerConfigParser
import core.game.system.timer.TimerRegistry
import core.game.system.timer.impl.AntiMacro
import core.game.system.timer.impl.Disease
import core.game.system.timer.impl.Poison
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.repository.Repository
import core.game.world.update.UpdateSequence
import core.network.IoSession
import core.network.packet.IoBuffer
import core.network.packet.PacketProcessor
import core.tools.Log

import java.net.URI
import java.nio.ByteBuffer

object TestUtils {
    var uidCounter = 0
    const val PLAYER_DEATH_TICKS = 14

    fun getMockPlayer(
        name: String,
        ironman: IronmanMode = IronmanMode.NONE,
        rights: Rights = Rights.ADMINISTRATOR,
        isBot: Boolean = false
    ): MockPlayer {
        val p = MockPlayer(name, isBot)
        p.ironmanManager.mode = ironman
        p.details.accountInfo.uid = uidCounter++
        p.isPlaying = true
        p.playerFlags.lastSceneGraph = p.location ?: ServerConstants.HOME_LOCATION
        Repository.addPlayer(p)
        //Update sequence has a separate list of players for some reason...
        UpdateSequence.renderablePlayers.add(p)
        p.details.rights = rights
        return p
    }

    fun getMockShop(name: String, general: Boolean, highAlch: Boolean, vararg stock: Item): Shop {
        return Shop(
            name,
            stock.map { ShopItem(it.id, it.amount, 100) }.toTypedArray(),
            general,
            highAlch = highAlch
        )
    }

    fun getMockTokkulShop(name: String, vararg stock: Item): Shop {
        return Shop(
            name,
            stock.map { ShopItem(it.id, it.amount, 100) }.toTypedArray(),
            currency = Items.TOKKUL_6529
        )
    }

    fun preTestSetup() {
        if (ServerConstants.DATA_PATH == null) {
            ServerConfigParser.parse(this::class.java.getResource("test.conf"))
            Cache.init(this::class.java.getResource("cache").path.toString())
            ConfigParser().prePlugin()
            ConfigParser().postPlugin()
            registerTimers()
        }
    }

    fun registerTimers() { //allow timers to be registered for use by tests
        TimerRegistry.registerTimer(Poison())
        TimerRegistry.registerTimer(Disease())
        TimerRegistry.registerTimer(CropGrowth())
        TimerRegistry.registerTimer(AntiMacro())
    }

    fun loadFile(path: String): URI? {
        return this::class.java.getResource(path)?.toURI()
    }

    fun advanceTicks(amount: Int, skipPulseUpdates: Boolean = true) {
        log(this::class.java, Log.FINE, "Advancing ticks by $amount.")
        for (i in 0 until amount) {
            GameWorld.majorUpdateWorker.handleTickActions(skipPulseUpdates)
        }
    }


    fun simulateInteraction(player: Player, target: Node, optionIndex: Int, iface: Int = -1, child: Int = -1) {
        when (target) {
            is GroundItem -> PacketProcessor.enqueue(
                core.network.packet.incoming.Packet.GroundItemAction(
                    player,
                    optionIndex,
                    target.id,
                    target.location.x,
                    target.location.y
                )
            )

            is Item -> PacketProcessor.enqueue(
                core.network.packet.incoming.Packet.ItemAction(
                    player,
                    optionIndex,
                    target.id,
                    target.slot,
                    iface,
                    child
                )
            )

            is NPC -> PacketProcessor.enqueue(
                core.network.packet.incoming.Packet.NpcAction(
                    player,
                    optionIndex,
                    target.clientIndex
                )
            )

            is core.game.node.scenery.Scenery -> PacketProcessor.enqueue(
                core.network.packet.incoming.Packet.SceneryAction(
                    player,
                    optionIndex,
                    target.id,
                    target.location.x,
                    target.location.y
                )
            )
        }
        advanceTicks(1, true)
    }
}

/**
 * Mock player
 *
 * @property isBot Indicates whether the player is a bot or a human.
 * @constructor Initializes a MockPlayer with a name and a bot status.
 *
 * @param name The name of the player.
 */
class MockPlayer(name: String, val isBot: Boolean) : Player(PlayerDetails(name)), AutoCloseable {
    var hasInit = false

    init {
        configureBasicProperties(); flagTutComplete(false); init(); flagTutComplete(true)
    }

    /**
     * Configure basic properties
     *
     */
    fun configureBasicProperties() {
        this.details.session = MockSession()
        this.location = ServerConstants.HOME_LOCATION
        this.properties.attackStyle = WeaponInterface.AttackStyle(0, WeaponInterface.BONUS_CRUSH)
        this.artificial = isBot
    }

    /**
     * This function is responsible for flagging the tutorial as complete.
     *
     * @param complete A Boolean value indicating whether the tutorial is complete or not.
     */
    fun flagTutComplete(complete: Boolean) {
        this.setAttribute("/save:tutorial:complete", complete)
        this.setAttribute("/save:rules:confirmed", complete)
    }

    override fun update() {
        //do nothing. This is for rendering stuff. We don't render a mock player. Not until the spaghetti is less spaghetti.
    }

    override fun init() {
        if (hasInit) return
        hasInit = true
        super.init()
    }

    override fun close() {
        Repository.removePlayer(this)
        UpdateSequence.renderablePlayers.remove(this)
        finishClear()
    }

    override fun setLocation(location: Location?) {
        super.setLocation(location)
        RegionManager.move(this)
        this.playerFlags.lastSceneGraph = location
    }

    /**
     * Relog function to handle user re-login process.
     *
     * @param ticksToWait The number of ticks to wait before re-logging. Default is -1, which indicates no wait.
     */
    fun relog(ticksToWait: Int = -1) {
        val json = PlayerSaver(this).populate()
        val parse = PlayerSaveParser(this)
        parse.saveFile = json

        close()
        timers.clearTimers()
        inventory.clear()
        bank.clear()
        equipment.clear()
        skills = Skills(this)
        savedData = SavedData(this)
        questRepository = QuestRepository(this)
        varpManager = VarpManager(this)
        varpMap.clear()
        saveVarp.clear()
        scripts = ScriptProcessor(this)
        clearAttributes()
        hasInit = false

        if (ticksToWait > 0) TestUtils.advanceTicks(ticksToWait, false)

        configureBasicProperties()
        parse.parseData()
        init()
    }

    override fun debug(string: String?) {
        log(this::class.java, Log.DEBUG, "[$name] -> Received Debug: $string")
    }
}

/**
 * MockSession class simulates an I/O session for handling packets.
 * It extends the IoSession class and manages received packets.
 */
class MockSession : IoSession(null, null) {
    val receivedPackets = ArrayList<Packet>()
    var disconnected = false

    /**
     * Packet data class represents a network packet.
     *
     * @property opcode The operation code of the packet.
     * @property payload The data contained in the packet.
     * @constructor Creates a Packet with the specified opcode and payload.
     */
    @Suppress("ArrayInDataClass")
    data class Packet(val opcode: Int, val payload: ByteArray)

    /**
     * Retrieves the remote address of the session.
     *
     * @return The remote address as a String, currently hardcoded to "127.0.0.1".
     */
    override fun getRemoteAddress(): String? {
        return "127.0.0.1"
    }

    /**
     * Writes data to the session.
     *
     * @param context The data to be written, expected to be of type IoBuffer.
     * @param instant A flag indicating whether the write should be immediate.
     */
    override fun write(context: Any, instant: Boolean) {
        if (context is IoBuffer) {
            receivedPackets.add(Packet(context.opcode(), context.array()))
        }
    }

    /**
     * Checks if a packet with the specified opcode is ready.
     *
     * @param opcode The opcode to check for.
     * @return True if a packet with the specified opcode is found, otherwise false.
     */
    fun hasPacketReady(opcode: Int): Boolean {
        for (pkt in receivedPackets) if (pkt.opcode == opcode) return true
        return false
    }

    /**
     * Retrieves all packets with the specified opcode.
     *
     * @param opcode The opcode to filter packets by.
     * @return A list of ByteArray containing the payloads of matching packets.
     */
    fun getPacketsWithOpcode(opcode: Int): ArrayList<ByteArray> {
        val list = ArrayList<ByteArray>()
        for (pkt in receivedPackets) if (pkt.opcode == opcode) list.add(pkt.payload)
        return list
    }

    /**
     * Retrieves a pair of ISAAC ciphers for encryption/decryption.
     *
     * @return An ISAACPair containing two ISAACCipher instances.
     */
    override fun getIsaacPair(): ISAACPair {
        return ISAACPair(ISAACCipher(intArrayOf(0)), ISAACCipher(intArrayOf(0)))
    }

    /**
     * Queues a ByteBuffer for processing.
     *
     * @param buffer The ByteBuffer to be queued.
     */
    override fun queue(buffer: ByteBuffer?) {}

    /**
     * Writes data to the session (no implementation provided).
     */
    override fun write() {}

    /**
     * Disconnects the session, setting the disconnected flag to true.
     */
    override fun disconnect() {
        disconnected = true
    }

    /**
     * Clear
     *
     */
    fun clear() {
        // Clear the list of received packets
        receivedPackets.clear()
    }

}
