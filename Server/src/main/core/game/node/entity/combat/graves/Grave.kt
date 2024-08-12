package core.game.node.entity.combat.graves

import core.api.clearHintIcon
import core.api.consts.NPCs
import core.api.registerHintIcon
import core.api.sendMessage
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.repository.Repository
import core.plugin.Initializable
import core.tools.colorize
import core.tools.secondsToTicks
import core.tools.ticksToSeconds

/**
 * Grave.
 */
@Initializable
class Grave : AbstractNPC {
    lateinit var type: GraveType
    private val items = ArrayList<GroundItem>()
    var ownerUsername: String = ""
    var ownerUid: Int = -1

    var ticksRemaining = -1

    constructor() : super(NPCs.GRAVESTONE_6571, Location.create(0, 0, 0), false)
    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return Grave(id, location)
    }

    override fun getIds(): IntArray {
        return GraveType.ids
    }

    /**
     * Configure type.
     */
    fun configureType(type: GraveType) {
        this.type = type
        this.transform(type.npcId)
        this.ticksRemaining = secondsToTicks(type.durationMinutes * 60)
    }

    /**
     * Initialize
     *
     * @param player The player object that will be initialized.
     * @param location The location where the player will be placed.
     * @param inventory The array of items that the player will have.
     */
    fun initialize(player: Player, location: Location, inventory: Array<Item>) {
        if (!GraveController.allowGenerate(player))
            return

        this.ownerUid = player.details.uid
        this.ownerUsername = player.username
        this.location = player.getAttribute("/save:original-loc", location)
        this.isRespawn = false
        this.isWalks = false
        this.isNeverWalks = true

        for (item in inventory) {
            if (GraveController.shouldRelease(item.id)) {
                sendMessage(player, "Your ${item.name.lowercase().replace("jar", "")} has escaped.")
                continue
            }

            if (GraveController.shouldCrumble(item.id)) {
                sendMessage(player, "Your ${item.name.lowercase()} has crumbled to dust.")
                continue
            }

            val finalItem = GraveController.checkTransform(item)

            val gi = GroundItemManager.create(finalItem, this.location, player)
            gi.isRemainPrivate = true
            gi.decayTime = secondsToTicks(type.durationMinutes * 60)
            this.items.add(gi)
        }

        if (items.isEmpty()) {
            clear()
            return
        }

        this.init()

        if (GraveController.activeGraves[ownerUid] != null) {
            val oldGrave = GraveController.activeGraves[ownerUid]
            oldGrave?.collapse()
        }

        GraveController.activeGraves[ownerUid] = this
        sendMessage(
            player,
            colorize("%RBecause of your current gravestone, you have ${type.durationMinutes} minutes to get your items back.")
        )
    }

    /**
     * Setup from json params
     *
     * @param playerUid Unique identifier for the player
     * @param ticks Number of ticks to process
     * @param location The player's current location in the game
     * @param items Array of items associated with the player
     * @param username The name of the player
     */
    fun setupFromJsonParams(playerUid: Int, ticks: Int, location: Location, items: Array<Item>, username: String) {
        this.ownerUid = playerUid
        this.ticksRemaining = ticks
        this.location = location
        this.isRespawn = false
        this.isWalks = false
        this.isNeverWalks = true
        this.ownerUsername = username

        for (item in items) {
            val gi = GroundItemManager.create(item, location, playerUid, GameWorld.ticks + ticksRemaining)
            gi.isRemainPrivate = true
            this.items.add(gi)
        }

        this.transform(type.npcId)
        this.init()
    }

    override fun tick() {
        //Grave should not do anything else on tick, that is all handled by GraveController.
        if (Repository.uid_map[ownerUid] != null) {
            val p = Repository.uid_map[ownerUid] ?: return
            registerHintIcon(p, this)
        }
    }

    /**
     * Add time
     *
     * @param ticks The number of ticks to add to the remaining time.
     */
    fun addTime(ticks: Int) {
        ticksRemaining += ticks
        for (gi in items) {
            gi.decayTime = ticksRemaining
        }
        if (ticksRemaining < 30)
            transform(type.npcId + 2)
        else if (ticksRemaining < 90)
            transform(type.npcId + 1)
        else
            transform(type.npcId)
    }

    /**
     * Collapse
     *
     */
    fun collapse() {
        for (item in items) {
            GroundItemManager.destroy(item)
        }
        clear()
        GraveController.activeGraves.remove(ownerUid)
        if (Repository.uid_map[ownerUid] != null) {
            val p = Repository.uid_map[ownerUid] ?: return
            clearHintIcon(p)
        }
    }

    /**
     * Demolish
     *
     */
    fun demolish() {
        val owner = Repository.uid_map[ownerUid] ?: return
        for (item in items) {
            if (!item.isRemoved)
                item.decayTime = secondsToTicks(45)
        }
        clear()
        sendMessage(owner, "It looks like it'll last another ${getFormattedTimeRemaining()}.")
        sendMessage(owner, "You demolish it anyway.")
        GraveController.activeGraves.remove(ownerUid)
        clearHintIcon(owner)
    }

    /**
     * Get items
     *
     * @return An array of GroundItem objects.
     */
    fun getItems(): Array<GroundItem> {
        return this.items.toTypedArray()
    }

    /**
     * Retrieve formatted text
     *
     * @return A formatted string with the owner's username and remaining time.
     */
    fun retrieveFormattedText(): String {
        return type.text
            .replace("@name", ownerUsername)
            .replace("@mins", getFormattedTimeRemaining())
    }

    /**
     * Get formatted time remaining
     *
     * @return A string representing the remaining time in a human-readable format.
     */
    fun getFormattedTimeRemaining(): String {
        val seconds = ticksToSeconds(ticksRemaining)
        val timeQty = if (seconds / 60 > 0) seconds / 60 else seconds
        val timeUnit = (if (seconds / 60 > 0) "minute" else "second") + if (timeQty > 1) "s" else ""
        return "$timeQty $timeUnit"
    }
}
