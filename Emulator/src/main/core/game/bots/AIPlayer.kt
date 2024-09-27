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
import core.net.packet.context.MessageContext
import core.tools.RandomFunction
import core.tools.StringUtils
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * Represents an artificial intelligence-controlled player within the game.
 * This class extends the Player class and is used to simulate player actions
 * and behaviors.
 */
open class AIPlayer @Suppress("deprecation") private constructor(name: String, l: Location, ignored: String?) : Player(PlayerDetails("/aip" + (currentUID + 1) + ":" + name)) {

    /**
     * Unique identifier for a user.
     * This variable holds an integer value that uniquely represents a user within the system.
     */
    val uid: Int

    /**
     * Represents the starting geographic location.
     *
     * This variable holds the initial location data which
     * can be used as the starting point in various geographical
     * computations or navigations. The location may include
     * attributes such as latitude, longitude, altitude, etc.
     *
     * Note: The setter method is private, hence `startLocation`
     * can only be modified internally within the class.
     */
    var startLocation: Location?

        get() = startLocation
        private set(startLocation) {
            super.startLocation = startLocation
        }

    /**
     * Represents the username of the AI player.
     *
     * This variable is used for identification and interaction within the game.
     */
    private val username: String

    /**
     * The `controler` variable holds a reference to a `Player` object, which
     * may be null. This variable is used to manage or control certain aspects
     * or actions of the `Player` instance in the application.
     */
    @JvmField
    var controler: Player? = null

    /**
     * Secondary constructor that initializes an object with a given Location.
     *
     * @param l location to initialize the object with
     */
    constructor(l: Location) : this(randomName, l, null)
    /**
     * Constructs an AIPlayer instance with a username generated from a random name retrieved
     * from the specified file and a starting location.
     *
     * @param fileName The name of the file from which to retrieve a random name.
     * @param l The starting location of the AIPlayer.
     */
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

    /**
     * Updates the random values associated with the AIPlayer instance, affecting appearance, direction, and combat level.
     *
     * This method performs the following actions:
     * - Randomly sets the gender of the appearance to either FEMALE or MALE.
     * - Calls a randomization function to change certain attributes of the AIPlayer instance.
     * - Sets the direction of the AIPlayer instance to a random value from the available directions.
     * - Updates the combat level based on the current skill levels.
     * - Synchronizes the appearance.
     */
    open fun updateRandomValues() {
        appearance.gender = if (RandomFunction.random(5) == 1) Gender.FEMALE else Gender.MALE
        val setTo = RandomFunction.random(0, 10)
        randomize(this, true)
        this.setDirection(Direction.values()[Random().nextInt(Direction.values().size)]) //Random facing dir
        getSkills().updateCombatLevel()
        appearance.sync()
    }

    /**
     * Updates the current state of the object.
     *
     * This method is intended to be overridden by subclasses to provide specific update logic.
     * The default implementation does not perform any operation and must be overridden to achieve
     * the desired behavior.
     *
     * It is recommended to call this method periodically or in response to certain events
     * to ensure that the object's state remains current.
     */
    override fun update() {
        return
    }

    /**
     * Sets the levels for an AI player with a focus on combat skills.
     *
     * This method initializes the player stats by setting random levels
     * for all skills, ensuring that levels are realistic and balanced.
     * Particular emphasis is placed on distributing levels across the core
     * combat skills including Hitpoints, Prayer, Defence, Attack, and Strength
     * based on specified constraints.
     */
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

    /**
     * Equips the player with a predefined set of armor items if they exist.
     * Each item is identified by a corresponding OSRS (Old School RuneScape) ID.
     * Items are mapped to their respective equipment slots as follows:
     * - SLOT_HAT
     * - SLOT_CAPE
     * - SLOT_AMULET
     * - SLOT_WEAPON
     * - SLOT_CHEST
     * - SLOT_SHIELD
     * - SLOT_LEGS
     * - SLOT_HANDS
     * - SLOT_FEET
     *
     * This method does not return a value. Items that do not exist or have
     * an invalid ID will not be equipped.
     */
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

    /**
     * Parses a specific value from the OSRScopyLine at the given index.
     *
     * @param index the zero-based index of the value to parse.
     * @return the integer value parsed from the OSRScopyLine at the specified index.
     */
    private fun parseOSRS(index: Int): Int {
        return OSRScopyLine!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[index].toInt()
    }

    /**
     * Equips the given item to the specified slot if it exists and is valid.
     *
     * @param e The item to equip. If the item is null or its name is "null" (case insensitive), it will not be equipped.
     * @param slot The slot number to equip the item to. If the item ID is not zero, it will be equipped to this slot.
     */
    private fun equipIfExists(e: Item?, slot: Int) {
        if (e == null || e.name.equals("null", ignoreCase = true)) {
            return
        }
        if (e.id != 0) equipment.replace(e, slot)
    }

    /**
     * Initializes the player with default settings and registers the player within the game system.
     *
     * This method configures the player's spawn location, opens default interface tabs, and updates
     * session and mapping information to include this player instance. Additionally, it sets various
     * player settings and states.
     *
     * The process includes:
     * - Setting the spawn location to the starting location.
     * - Opening the default interface tabs.
     * - Registering the player instance in the session and bot mapping.
     * - Toggling the 'run' setting to true.
     * - Randomizing certain settings.
     * - Updating the last seen scene graph location.
     *
     * The method also calls the superclass's init method to ensure any necessary parent class
     * initialization processes are executed.
     */
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


    /**
     * Initiates a follow action where the current entity starts following the specified entity.
     *
     * @param e the entity to follow; if null, no action is taken.
     */
    fun follow(e: Entity?) {
        pulseManager.run(object : MovementPulse(this, e, DestinationFlag.FOLLOW_ENTITY) {
            /**
             * Executes the pulse action by making the AI player face a specified entity.
             *
             * @return false, indicating that the pulse action does not complete.
             */
            override fun pulse(): Boolean {
                face(e)
                return false
            }
        }, PulseType.STANDARD)
    }

    /**
     * Initiates a random walk for an entity around a specified point within a given radius.
     *
     * @param point The central location around which the random walk will be performed.
     * @param radius The radius within which the entity is allowed to walk randomly.
     */
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

    /**
     * Initiates a random walk from the current location of the object.
     *
     * The walk is restricted within the boundaries defined by the radius parameters
     * along the X and Y axes.
     *
     * @param radiusX The radius along the X-axis within which the random walk will occur.
     * @param radiusY The radius along the Y-axis within which the random walk will occur.
     */
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

    /**
     * Directs the AIPlayer to walk to the specified coordinates using a smart pathfinding algorithm.
     *
     * @param x the x-coordinate of the destination.
     * @param y the y-coordinate of the destination.
     */
    fun walkToPosSmart(x: Int, y: Int) {
        walkToPosSmart(Location(x, y))
    }

    /**
     * Guides the entity to walk to the specified location intelligently.
     *
     * @param loc The destination location to which the entity should walk. Can be null.
     */
    fun walkToPosSmart(loc: Location?) {
        Pathfinder.find(this, loc, true, Pathfinder.SMART).walk(this)
    }

    /**
     * Directs the player to walk to the specified (x, y) coordinates.
     *
     * @param x The x-coordinate of the destination.
     * @param y The y-coordinate of the destination.
     */
    fun walkPos(x: Int, y: Int) {
        Pathfinder.find(this, Location(x, y))
    }

    /**
     * Checks if the current combat victim is a player.
     *
     * @return true if the victim is a player, false otherwise.
     */
    fun checkVictimIsPlayer(): Boolean {
        if (properties.combatPulse.getVictim() != null) if (properties.combatPulse.getVictim()!!
                .isPlayer
        ) return true
        return false
    }

    /**
     * Method invoked on each game tick to update the state of the player.
     *
     * The method first calls the base class implementation of `tick()`. Then, it checks if the player is in the wilderness.
     * If the player is in the wilderness, it updates the player's wilderness level based on their current location.
     *
     * Additionally, it checks if the player's lifepoints are less than or equal to zero.
     * If the player's lifepoints are depleted, it triggers the deregistration process for the player (currently commented out).
     */
    override fun tick() {
        super.tick()
        if (skullManager.isWilderness) {
            skullManager.level = WildernessZone.getWilderness(this)
        }
        if (getSkills().lifepoints <= 0) {
            //deregister(this.uid);
        }
    }

    /**
     * Retrieves an item from the inventory by its unique identifier.
     *
     * @param id the unique identifier of the item to be retrieved
     * @return the item with the specified identifier, or null if the item is not found
     */
    fun getItemById(id: Int): Item? {
        for (i in 0..27) {
            val item = inventory[i]
            if (item != null) {
                if (item.id == id) return item
            }
        }
        return null
    }

    /**
     * Handles the incoming chat messages.
     *
     * @param ctx An optional MessageContext containing information about the incoming chat.
     */
    fun handleIncomingChat(ctx: MessageContext?) {
    }


    /**
     * Retrieves a list of nodes within a specified range that match a given entry.
     *
     * @param range The maximum distance to search for nodes.
     * @param entry The identifier of the nodes to find.
     * @return A list of nodes within the specified range that match the given entry.
     */
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

    /**
     * Retrieves a list of nodes within a given range that match a specified list of entry IDs.
     *
     * @param range The range within which to search for nodes.
     * @param entrys A list of node entry IDs to filter the nodes by.
     * @return An ArrayList of nodes that are within the specified range and match the given entry IDs.
     */
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

    /**
     * Finds the closest node within a given range that has a specific entry and direction.
     *
     * @param range the range to search within
     * @param entry the entry identifier to search for
     * @param direction the direction to filter the nodes
     * @return the closest node that matches the criteria, or null if no such node is found
     */
    fun getClosestNodeWithEntryAndDirection(range: Int, entry: Int, direction: Direction): Node? {
        val nodeList = getNodeInRange(range, entry)
        if (nodeList.isEmpty()) {
            return null
        }
        val node = getClosestNodeinNodeListWithDirection(nodeList, direction)
        return node
    }

    /**
     * Searches for the closest node with a given entry within a specified range.
     *
     * @param range The search radius within which nodes are considered.
     * @param entry The entry identifier to match nodes against.
     * @return The closest node with the specified entry within the specified range, or null if none found.
     */
    fun getClosestNodeWithEntry(range: Int, entry: Int): Node? {
        val nodeList = getNodeInRange(range, entry)
        if (nodeList.isEmpty()) {
            return null
        }
        val node = getClosestNodeinNodeList(nodeList)
        return node
    }

    /**
     * Finds the closest node within a specified range that contains an entry from a given list.
     *
     * @param range The range within which nodes are to be considered.
     * @param entrys The list of entries to check against the nodes.
     * @return The closest node that contains an entry from the list, or `null` if no such node exists.
     */
    fun getClosestNodeWithEntry(range: Int, entrys: List<Int>): Node? {
        val nodeList = getNodeInRange(range, entrys)
        if (nodeList.isEmpty()) {
            return null
        }
        val node = getClosestNodeinNodeList(nodeList)
        return node
    }

    /**
     * Retrieves the closest creature within the specified radius.
     *
     * @param radius The search radius within which to look for creatures.
     * @return The closest Node representing the creature found, or null if no creature is found within the radius.
     */
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

    /**
     * Finds the closest creature within the given radius that matches the specified entry ID.
     *
     * @param radius The radius within which to search for creatures.
     * @param entry The ID of the creature to find.
     * @return The closest creature that matches the specified entry ID, or null if no such creature is found.
     */
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

    /**
     * Finds the closest creature (NPC) within a specified radius that matches one of the given entries.
     *
     * @param radius The search radius.
     * @param entrys A list of NPC entries to match.
     * @return The closest matching NPC, or null if no matching NPC is found.
     */
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

    /**
     * Finds the closest node from a list of nodes that matches the specified direction.
     *
     * @param nodes The list of nodes to search through.
     * @param direction The direction to match.
     * @return The closest node that matches the specified direction, or null if no such node is found or the list is empty.
     */
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

    /**
     * Finds the closest node from a list of nodes based on the current location.
     *
     * @param nodes The list of nodes to search through.
     * @return The closest node, or null if the list is empty.
     */
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

    /**
     * Removes the mapping for the user interface descriptor (UID) from botMapping.
     * Afterward, invokes the superclass `clear` method to perform additional cleanup operations.
     */
    override fun clear() {
        botMapping.remove(uid)
        super.clear()
    }

    /**
     * Resets the current state of the object.
     *
     * If the player flags indicate that the scene graph needs an update,
     * this method will save the current location to the lastSceneGraph attribute.
     * Calls the superclass's reset method to proceed with standard reset operations.
     */
    override fun reset() {
        if (playerFlags.isUpdateSceneGraph) {
            playerFlags.lastSceneGraph = getLocation()
        }
        super.reset()
    }

    /**
     * Finalizes the death of the AI player.
     *
     * This method invokes the `finalizeDeath` method of the superclass to handle common death logic,
     * followed by restoring the player's stats and state to full.
     *
     * @param killer the entity that caused the player's death
     */
    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        fullRestore()
    }

    /**
     * Retrieves the username of the AIPlayer.
     *
     * @return the username of the AIPlayer
     */
    override fun getUsername(): String {
        return username
    }


    /**
     * Interacts with a given node.
     *
     * @param n The node to interact with.
     */
    fun interact(n: Node?) {
        // InteractionPacket.handleObjectInteraction(this, 0, n.getLocation(), n.getId());
    }

    /**
     * Companion object containing utility functions and properties for managing bot names and mappings.
     */
    companion object {
        /**
         * Stores the current unique identifier for the AIPlayer instances.
         * This value is used to track and manage AIPlayer objects dynamically within the game.
         */
        private var currentUID = 0x1

        /**
         * A mutable list that stores names of bots used in the AIPlayer class.
         *
         * This list is populated by the function `loadNames(fileName: String)`, which reads bot
         * names from a specified file and adds them to the list.
         */
        private val botNames: MutableList<String> = ArrayList()
        /**
         * A map that holds AIPlayer instances, indexed by their unique identifier (uid).
         *
         * This map is used to manage and retrieve AIPlayer instances efficiently during runtime.
         * The uid serves as a key, allowing quick lookup, insertion, and deletion operations.
         */
        private val botMapping: MutableMap<Int, AIPlayer> = HashMap()
        /**
         * Stores a line of text from an OSRS-related file. It is used in various parsing and retrieval operations
         * to obtain information based on the content of the line.
         *
         * The line is typically selected randomly from a file and may hold different types of data sorted by colons.
         */
        private var OSRScopyLine: String? = null

        init {
            loadNames("botnames.txt")
        }

        /**
         * Loads bot names from the specified file and adds them to the botNames list.
         *
         * @param fileName the name of the file containing bot names to be loaded
         */
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

        /**
         * Property that provides a random name from the list of botNames. Once a name is retrieved, it is removed from the list.
         *
         * Uses RandomFunction.random to generate a random index based on the current size of the botNames list.
         * This ensures that each name is unique and not reused.
         *
         * @return A randomly selected name from the botNames list.
         */
        val randomName: String
            get() {
                val index = (RandomFunction.random(botNames.size))
                val name = botNames[index]
                botNames.removeAt(index)
                return name
            }

        /**
         * Reads a file and updates the OSRScopyLine with a randomly selected non-empty, non-comment line.
         *
         * @param fileName the name of the file to be read. The file should be located in the path specified by Configuration.BOT_DATA_PATH.
         */
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

        /**
         * Retrieves a random name from a file, ensuring that the name does not contain certain undesirable characteristics.
         *
         * @param fileName The name of the file from which to retrieve the random name. Default is "namesandarmor.txt".
         * @return A randomly retrieved name from the file.
         */
        private fun retrieveRandomName(fileName: String = "namesandarmor.txt"): String {
            do {
                updateRandomOSRScopyLine(fileName)
            } while (OSRScopyLine!!.startsWith("#") || OSRScopyLine!!.contains("_") || OSRScopyLine!!.contains(" ")) //Comment
            return OSRScopyLine!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0]
        }

        /**
         * Deregisters a player from the system based on the given unique ID (uid).
         * The method first checks if the player exists in the botMapping using the provided uid.
         * If the player is found, their data is cleared and they are removed from the players list.
         * If the player is not found, an error message will be logged.
         *
         * @param uid The unique identifier of the player to be deregistered.
         */
        fun deregister(uid: Int) {
            val player = botMapping[uid]
            if (player != null) {
                player.clear()
                players.remove(player)
                return
            }
            //log(this.getClass(), Log.ERR,  "Could not deregister AIP#" + uid + ": UID not added to the mapping!");
        }

        /**
         * Retrieves the AIPlayer associated with the given unique identifier (uid).
         *
         * @param uid The unique identifier for the AI player to be retrieved.
         * @return The AIPlayer associated with the specified uid, or null if no such player exists.
         */
        fun get(uid: Int): AIPlayer? {
            return botMapping[uid]
        }
    }
}
