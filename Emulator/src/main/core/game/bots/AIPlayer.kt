package core.game.bots

import content.region.misc.tutorial.handlers.CharacterDesign.randomize
import core.Configuration
import core.game.container.impl.EquipmentContainer
import core.game.interaction.DestinationFlag
import core.game.interaction.MovementPulse
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.impl.PulseType
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.PlayerDetails
import core.game.node.entity.player.link.appearance.Gender
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalNpcs
import core.game.world.map.RegionManager.getObject
import core.game.world.map.path.Pathfinder
import core.game.world.map.zone.impl.WildernessZone
import core.game.world.repository.Repository.players
import core.network.packet.context.MessageContext
import core.tools.RandomFunction
import core.tools.StringUtils
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * Represents an Artifical inteligent player.
 * @author Emperor
 */
open class AIPlayer @Suppress("deprecation") private constructor(name: String, l: Location, ignored: String?) : Player(PlayerDetails("/aip" + (currentUID + 1) + ":" + name)) {

    val uid: Int

    var startLocation: Location?

        get() = startLocation
        private set(startLocation) {
            super.startLocation = startLocation
        }

    private val username: String

    @JvmField
    var controler: Player? = null

    constructor(l: Location) : this(randomName, l, null)
    constructor(fileName: String, l: Location) : this(retrieveRandomName(fileName), l, null)

    init {
        super.setLocation(l.also { startLocation = it })
        super.artificial = true
        super.getDetails().session = ArtificialSession.singleton
        players.add(this)
        this.username = StringUtils.formatDisplayName(name + (currentUID + 1))
        this.uid = currentUID++
        this.updateRandomValues()
        this.init()
    }

    open fun updateRandomValues() {
        appearance.gender = if (RandomFunction.random(5) == 1) Gender.FEMALE else Gender.MALE
        val setTo = RandomFunction.random(0, 10)
        randomize(this, true)
        this.setDirection(Direction.values()[Random().nextInt(Direction.values().size)]) //Random facing dir
        getSkills().updateCombatLevel()
        appearance.sync()
    }

    override fun update() {
        return
    }

    private fun setLevels() {
        //Create realistic player stats
        val maxLevel = RandomFunction.random(1, min(parseOSRS(1), 99))
        for (i in 0 until Skills.NUM_SKILLS) {
            getSkills().setStaticLevel(i, RandomFunction.linearDecreaseRand(maxLevel))
        }
        var combatLevelsLeft = parseOSRS(1)
        val hitpoints = max(
            RandomFunction.randomDouble(10.0, min(maxLevel.toDouble(), (combatLevelsLeft * 4).toDouble())),
            10.0
        ).toInt()
        combatLevelsLeft = (combatLevelsLeft - 0.25 * hitpoints).toInt()
        val prayer = if (combatLevelsLeft > 0) RandomFunction.random(
            min(maxLevel.toDouble(), (combatLevelsLeft * 8).toDouble()).toInt()
        ) else 1
        combatLevelsLeft = (combatLevelsLeft - 0.125 * prayer).toInt()
        val defence = if (combatLevelsLeft > 0) RandomFunction.random(
            min(maxLevel.toDouble(), (combatLevelsLeft * 4).toDouble()).toInt()
        ) else 1
        combatLevelsLeft = (combatLevelsLeft - 0.25 * defence).toInt()

        combatLevelsLeft = min(combatLevelsLeft.toDouble(), 199.0).toInt()

        val attack = if (combatLevelsLeft > 0) RandomFunction.normalRandDist(
            min(maxLevel.toDouble(), (combatLevelsLeft * 3).toDouble()).toInt()
        ) else 1
        val strength = if (combatLevelsLeft > 0) combatLevelsLeft * 3 - attack else 1

        getSkills().setStaticLevel(Skills.HITPOINTS, hitpoints)
        getSkills().setStaticLevel(Skills.PRAYER, prayer)
        getSkills().setStaticLevel(Skills.DEFENCE, defence)
        getSkills().setStaticLevel(Skills.ATTACK, attack)
        getSkills().setStaticLevel(Skills.STRENGTH, strength)
        getSkills().setStaticLevel(Skills.RANGE, combatLevelsLeft / 2)
        getSkills().setStaticLevel(Skills.MAGIC, combatLevelsLeft / 2)
    }

    private fun giveArmor() {
        equipIfExists(Item(parseOSRS(2)), EquipmentContainer.SLOT_HAT)
        equipIfExists(Item(parseOSRS(3)), EquipmentContainer.SLOT_CAPE)
        equipIfExists(Item(parseOSRS(4)), EquipmentContainer.SLOT_AMULET)
        equipIfExists(Item(parseOSRS(5)), EquipmentContainer.SLOT_WEAPON)
        equipIfExists(Item(parseOSRS(6)), EquipmentContainer.SLOT_CHEST)
        equipIfExists(Item(parseOSRS(7)), EquipmentContainer.SLOT_SHIELD)
        equipIfExists(Item(parseOSRS(9)), EquipmentContainer.SLOT_LEGS)
        equipIfExists(Item(parseOSRS(11)), EquipmentContainer.SLOT_HANDS)
        equipIfExists(Item(parseOSRS(12)), EquipmentContainer.SLOT_FEET)
    }

    private fun parseOSRS(index: Int): Int {
        return OSRScopyLine!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[index].toInt()
    }

    private fun equipIfExists(e: Item?, slot: Int) {
        if (e == null || e.name.equals("null", ignoreCase = true)) {
            return
        }
        if (e.id != 0) equipment.replace(e, slot)
    }

    override fun init() {
        properties.spawnLocation = startLocation
        interfaceManager.openDefaultTabs()
        session.setObject(this)
        botMapping[uid] = this
        super.init()
        settings.isRunToggled = true
        randomize(this, false)
        playerFlags.lastSceneGraph = location
    }


    fun follow(e: Entity?) {
        pulseManager.run(object : MovementPulse(this, e, DestinationFlag.FOLLOW_ENTITY) {
            override fun pulse(): Boolean {
                face(e)
                return false
            }
        }, PulseType.STANDARD)
    }

    fun randomWalkAroundPoint(point: Location, radius: Int) {
        Pathfinder.find(
            this,
            point.transform(
                RandomFunction.random(radius, (radius * -1)),
                RandomFunction.random(radius, (radius * -1)),
                0
            ),
            true,
            Pathfinder.SMART
        ).walk(
            this
        )
    }

    override fun randomWalk(radiusX: Int, radiusY: Int) {
        Pathfinder.find(
            this,
            getLocation().transform(
                RandomFunction.random(radiusX, (radiusX * -1)),
                RandomFunction.random(radiusY, (radiusY * -1)),
                0
            ), false, Pathfinder.SMART
        ).walk(
            this
        )
    }

    fun walkToPosSmart(x: Int, y: Int) {
        walkToPosSmart(Location(x, y))
    }

    fun walkToPosSmart(loc: Location?) {
        Pathfinder.find(this, loc, true, Pathfinder.SMART).walk(this)
    }

    fun walkPos(x: Int, y: Int) {
        Pathfinder.find(this, Location(x, y))
    }

    fun checkVictimIsPlayer(): Boolean {
        if (properties.combatPulse.getVictim() != null) if (properties.combatPulse.getVictim()!!
                .isPlayer
        ) return true
        return false
    }

    override fun tick() {
        super.tick()
        if (skullManager.isWilderness) {
            skullManager.level = WildernessZone.getWilderness(this)
        }
        if (getSkills().lifepoints <= 0) {
            //deregister(this.uid);
        }
    }

    fun getItemById(id: Int): Item? {
        for (i in 0..27) {
            val item = inventory[i]
            if (item != null) {
                if (item.id == id) return item
            }
        }
        return null
    }

    fun handleIncomingChat(ctx: MessageContext?) {
    }


    private fun getNodeInRange(range: Int, entry: Int): ArrayList<Node> {
        val meX = getLocation().x
        val meY = getLocation().y
        val nodes = ArrayList<Node>()
        for (npc in getLocalNpcs(this, range)) {
            if (npc.id == entry) nodes.add(npc)
        }
        for (x in 0 until range) {
            for (y in 0 until range - x) {
                val node: Node? = getObject(0, meX + x, meY + y)
                if (node != null) if (node.id == entry) nodes.add(node)
                val node2: Node? = getObject(0, meX + x, meY - y)
                if (node2 != null) if (node2.id == entry) nodes.add(node2)
                val node3: Node? = getObject(0, meX - x, meY + y)
                if (node3 != null) if (node3.id == entry) nodes.add(node3)
                val node4: Node? = getObject(0, meX - x, meY - y)
                if (node4 != null) if (node4.id == entry) nodes.add(node4)
            }
        }
        return nodes
    }

    private fun getNodeInRange(range: Int, entrys: List<Int>): ArrayList<Node> {
        val meX = getLocation().x
        val meY = getLocation().y

        //int meX2 = this.getLocation().getX();
        val nodes = ArrayList<Node>()
        for (npc in getLocalNpcs(this, range)) {
            if (entrys.contains(npc.id)) nodes.add(npc)
        }
        for (x in 0 until range) {
            for (y in 0 until range - x) {
                val node: Node? = getObject(0, meX + x, meY + y)
                if (node != null) if (entrys.contains(node.id)) nodes.add(node)
                val node2: Node? = getObject(0, meX + x, meY - y)
                if (node2 != null) if (entrys.contains(node2.id)) nodes.add(node2)
                val node3: Node? = getObject(0, meX - x, meY + y)
                if (node3 != null) if (entrys.contains(node3.id)) nodes.add(node3)
                val node4: Node? = getObject(0, meX - x, meY - y)
                if (node4 != null) if (entrys.contains(node4.id)) nodes.add(node4)
            }
        }
        return nodes
    }

    fun getClosestNodeWithEntryAndDirection(range: Int, entry: Int, direction: Direction): Node? {
        val nodeList = getNodeInRange(range, entry)
        if (nodeList.isEmpty()) {
            return null
        }
        val node = getClosestNodeinNodeListWithDirection(nodeList, direction)
        return node
    }

    fun getClosestNodeWithEntry(range: Int, entry: Int): Node? {
        val nodeList = getNodeInRange(range, entry)
        if (nodeList.isEmpty()) {
            return null
        }
        val node = getClosestNodeinNodeList(nodeList)
        return node
    }

    fun getClosestNodeWithEntry(range: Int, entrys: List<Int>): Node? {
        val nodeList = getNodeInRange(range, entrys)
        if (nodeList.isEmpty()) {
            return null
        }
        val node = getClosestNodeinNodeList(nodeList)
        return node
    }

    fun getClosesCreature(radius: Int): Node? {
        var distance = radius + 1
        var npcReturn: Node? = null
        for (npc in getLocalNpcs(this, radius)) {
            val distanceToNpc = npc.location.getDistance(this.getLocation())
            if ((distanceToNpc) < distance) {
                distance = distanceToNpc.toInt()
                npcReturn = npc
            }
        }
        return npcReturn
    }

    fun getClosesCreature(radius: Int, entry: Int): Node? {
        var distance = radius + 1
        var npcReturn: Node? = null
        for (npc in getLocalNpcs(this, radius)) {
            val distanceToNpc = npc.location.getDistance(this.getLocation())
            if (npc.id == entry) {
                if ((distanceToNpc) < distance) {
                    distance = distanceToNpc.toInt()
                    npcReturn = npc
                }
            }
        }
        return npcReturn
    }

    fun getClosesCreature(radius: Int, entrys: ArrayList<Int?>): Node? {
        var distance = radius + 1
        var npcReturn: Node? = null
        for (npc in getLocalNpcs(this, radius)) {
            val distanceToNpc = npc.location.getDistance(this.getLocation())
            if (entrys.contains(npc.id)) {
                if ((distanceToNpc) < distance) {
                    distance = distanceToNpc.toInt()
                    npcReturn = npc
                }
            }
        }
        return npcReturn
    }

    private fun getClosestNodeinNodeListWithDirection(nodes: ArrayList<Node>, direction: Direction): Node? {
        if (nodes.isEmpty()) {
            return null
        }

        var distance = 0.0
        var nodeReturn: Node? = null
        for (node in nodes) {
            val nodeDistance = getLocation().getDistance(node.location)
            if ((nodeReturn == null || nodeDistance < distance) && node.direction == direction) {
                distance = nodeDistance
                nodeReturn = node
            }
        }
        return nodeReturn
    }

    private fun getClosestNodeinNodeList(nodes: ArrayList<Node>): Node? {
        if (nodes.isEmpty()) {
            return null
        }

        var distance = 0.0
        var nodeReturn: Node? = null
        for (node in nodes) {
            val nodeDistance = getLocation().getDistance(node.location)
            if (nodeReturn == null || nodeDistance < distance) {
                distance = nodeDistance
                nodeReturn = node
            }
        }
        return nodeReturn
    }

    override fun clear() {
        botMapping.remove(uid)
        super.clear()
    }

    override fun reset() {
        if (playerFlags.isUpdateSceneGraph) {
            playerFlags.lastSceneGraph = getLocation()
        }
        super.reset()
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        fullRestore()
    }

    override fun getUsername(): String {
        return username
    }


    fun interact(n: Node?) {
        // InteractionPacket.handleObjectInteraction(this, 0, n.getLocation(), n.getId());
    }

    companion object {
        private var currentUID = 0x1

        private val botNames: MutableList<String> = ArrayList()
        private val botMapping: MutableMap<Int, AIPlayer> = HashMap()
        private var OSRScopyLine: String? = null

        init {
            loadNames("botnames.txt")
        }

        fun loadNames(fileName: String) {
            try {
                val sc = Scanner(File(Configuration.BOT_DATA_PATH + fileName))
                while (sc.hasNextLine()) {
                    botNames.add(sc.nextLine())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val randomName: String
            get() {
                val index = (RandomFunction.random(botNames.size))
                val name = botNames[index]
                botNames.removeAt(index)
                return name
            }

        fun updateRandomOSRScopyLine(fileName: String) {
            val rand = Random()
            var n = 0
            try {
                val sc = Scanner(File(Configuration.BOT_DATA_PATH + fileName))
                while (sc.hasNext()) {
                    ++n
                    val line = sc.nextLine()
                    if (rand.nextInt(n) == 0) { //Chance of overwriting line is lower and lower
                        if (line.length < 3 || line.startsWith("#")) //probably an empty line
                        {
                            continue
                        }
                        OSRScopyLine = line
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        private fun retrieveRandomName(fileName: String = "namesandarmor.txt"): String {
            do {
                updateRandomOSRScopyLine(fileName)
            } while (OSRScopyLine!!.startsWith("#") || OSRScopyLine!!.contains("_") || OSRScopyLine!!.contains(" ")) //Comment
            return OSRScopyLine!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0]
        }

        fun deregister(uid: Int) {
            val player = botMapping[uid]
            if (player != null) {
                player.clear()
                players.remove(player)
                return
            }
            //log(this.getClass(), Log.ERR,  "Could not deregister AIP#" + uid + ": UID not added to the mapping!");
        }

        fun get(uid: Int): AIPlayer? {
            return botMapping[uid]
        }
    }
}
