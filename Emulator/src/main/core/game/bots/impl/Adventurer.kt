package core.game.bots.impl

import core.Configuration
import core.game.bots.AIRepository
import core.game.bots.CombatBotAssembler
import core.game.bots.Script
import core.game.interaction.DestinationFlag
import core.game.interaction.IntType
import core.game.interaction.InteractionListeners
import core.game.interaction.MovementPulse
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.*
import core.tools.RandomFunction
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.io.File
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

/**
 * The `Adventurer` class represents a character in the game with specific skills, inventory,
 * and behaviors aimed at exploring locations, engaging in combat, and interacting with the game environment.
 */
class Adventurer(val style: CombatStyle) : Script() {

    /**
     * Represents the city location for the adventurer.
     *
     * The `city` variable holds the location where the adventurer is currently situated.
     * Default value is set to Lumbridge.
     */
    var city: Location = lumbridge
    /**
     * The `ticks` variable is used to keep track of incremental time units.
     * It is typically initialized to 0 and is incremented as time progresses
     * in the application's context, such as in a game loop or event-driven
     * scenarios.
     */
    var ticks = 0
    /**
     * Indicates whether the adventurer is a fresh spawn.
     *
     * This flag is used to determine if the adventurer is in the initial state after being created or teleported.
     * It is set to `true` when the adventurer is first created or teleported, and can be used in logic that should
     * only apply to newly spawned adventurers.
     */
    var freshspawn = true
    /**
     * Indicates whether an item has been sold.
     *
     * This boolean variable should be set to true when the item has been
     * successfully sold, and false when the item is available or the sale
     * has not yet occurred.
     */
    var sold = false
    /**
     * Represents a specific location.
     * The location is randomly determined upon initialization.
     * It either initializes to coordinates (3165, 3487, 0) or (3164, 3492, 0).
     */
    private val geloc: Location = if (Random.nextBoolean()) {
        Location.create(3165, 3487, 0)
    } else {
        Location.create(3164, 3492, 0)
    }

    /**
     * Tracks the number of iterations or actions performed by the Adventurer.
     *
     * This variable is incremented in the `immerse` function to keep track
     * of the number of ticks since the last reset or significant event.
     */
    var counter: Int = 0
    /**
     * The `random` variable generates a random integer value
     * between 5 and 30, inclusive, each time it is accessed.
     *
     * This range can be modified by changing the values in the
     * (start..end) range definition.
     */
    var random: Int = (5..30).random()

    /**
     * Determines the type of combat bot based on the specified combat style.
     * The possible values are MELEE, MAGE, and RANGE.
     */
    val type = when (style) {
        CombatStyle.MELEE -> CombatBotAssembler.Type.MELEE
        CombatStyle.MAGIC -> CombatBotAssembler.Type.MAGE
        CombatStyle.RANGE -> CombatBotAssembler.Type.RANGE
    }

    init {
        skills[Skills.AGILITY] == 99
        inventory.add(Item(1359))
        skills[Skills.WOODCUTTING] == 95
        inventory.add(Item(590))
        skills[Skills.FISHING] == 90
        inventory.add(Item(1271))
        skills[Skills.MINING] == 90
        skills[Skills.SLAYER] == 90
    }

    /**
     * Returns a string representation of the Adventurer bot including its name, location, state, and city.
     * @return A string that describes the name, location, state, and city of the bot.
     */
    override fun toString(): String {
        return "${bot!!.name} is an Adventurer bot at ${bot!!.location}! State: $state - City: $city"
    }

    /**
     * The current state of the Adventurer bot.
     *
     * The state variable is used to determine the current activity or task
     * that the bot is performing. It is of type Adventurer.State and can represent
     * various phases such as START, EXPLORE, FIND_BANK, IDLE_BANKS, etc.
     */
    var state = State.START

    /**
     * Returns a random city from the list of available cities.
     *
     * @return A randomly selected city from the available cities.
     */
    fun getRandomCity(): Location {
        return cities.random()
    }

    /**
     * Retrieves a random Point of Interest (POI) location.
     *
     * @return a Location object representing a random POI.
     */
    fun getRandomPoi(): Location {
        return pois.random()
    }

    /**
     * The `immerse` function drives the bot's behavior for either combat or resource gathering.
     * This method increments a counter and changes states when certain conditions are met.
     * It handles both item looting and inventory management, such as teleporting to a bank
     * or gathering resources like logs or ores.
     *
     * Behavior Details:
     * - If `counter` reaches 180, the bot state changes to TELEPORTING.
     * - When ground items are available, the bot either attacks NPCs within a radius or
     *   sets the state to LOOT_DELAY.
     * - If the bot's inventory is full, it determines if it should teleport to a random city
     *   or walk to the closest bank, based on the bot's current location.
     * - If the inventory is not full, it gathers resources like rocks or trees by mining or chopping.
     */
//TODO: Optimise and adjust how bots handle picking up ground items further.
    fun immerse() {
        if (counter++ == 180) {
            state = State.TELEPORTING
        }
        val items = AIRepository.groundItems[bot]
        if (Random.nextBoolean()) {
            if (items.isNullOrEmpty()) {
                scriptAPI!!.attackNpcsInRadius(bot!!, 8)
                state = State.LOOT_DELAY
            }
            if (bot!!.inventory.isFull) {
                if (bankMap[city] == null) {
                    scriptAPI!!.teleport(getRandomCity().also { city = it })
                } else {
                    if (bankMap[city]?.insideBorder(bot) == true) {
                        state = State.FIND_BANK
                    } else {
                        scriptAPI!!.walkTo(bankMap[city]?.randomLoc ?: Location(0, 0, 0))
                    }
                }
            }

        } else {
            if (bot!!.inventory.isFull) {
                if (bankMap[city] == null) {
                    scriptAPI!!.teleport(getRandomCity().also { city = it })
                } else {
                    if (bankMap[city]?.insideBorder(bot) == true) {
                        state = State.FIND_BANK
                    } else {
                        scriptAPI!!.walkTo(bankMap[city]?.randomLoc ?: Location(0, 0, 0))
                    }
                }
            } else {
                val resources = listOf(
                    "Rocks", "Tree", "Oak", "Willow", "Maple tree", "Yew", "Magic tree", "Teak", "Mahogany"
                )
                val resource = scriptAPI!!.getNearestNodeFromList(resources, true)
                if (resource != null) {
                    if (resource.name.contains("ocks")) InteractionListeners.run(
                        resource.id, IntType.SCENERY, "mine", bot!!, resource
                    )
                    else InteractionListeners.run(resource.id, IntType.SCENERY, "chop down", bot!!, resource)
                }
            }
        }
        return
    }

    /**
     * Refreshes the game state by performing the following actions:
     *
     * - Teleports the character to the predefined location (Lumbridge).
     * - Sets the `freshspawn` flag to true, indicating a fresh spawn state.
     * - Updates the current state of the Adventurer to `START`.
     */
    fun refresh() {
        scriptAPI!!.teleport(lumbridge)
        freshspawn = true
        state = State.START
    }

    /**
     * Indicates whether the current point of interest (POI) is active or not.
     *
     * A value of `true` means the POI is currently active or selected,
     * while a value of `false` means it is inactive or not selected.
     */
    var poi = false
    /**
     * Represents the location of the player object in the game environment.
     * The default location is set to Karamja, which is a predefined spot
     * within the Adventurer bot implementation.
     */
    var poiloc = karamja


    /**
     * Handles the periodic actions or state changes of an in-game bot.
     * The method increments the `ticks` counter and performs various actions depending on the current state of the bot.
     *
     * Increments the `ticks` counter and checks if it has reached a limit of 800, at which point it resets and calls `refresh`.
     * Checks for common stuck locations every 30 ticks, resolving if the bot is within one.
     *
     * Handles actions based on the bot's current state, which can be one of:
     * - LOOT_DELAY: Initiates a short delay before proceeding to the LOOT state.
     * - LOOT: Collects items if the bot's inventory is not full, otherwise moves to the EXPLORE state.
     * - START: Determines whether to walk to the initial location or transition to teleporting to a random city.
     * - TELEPORTING: Resets tick counters and teleports the bot to a city or point of interest.
     * - EXPLORE: Handles random exploration, interactions with players, and immersion, while checking various locations and probabilities.
     * - GE: Manages interactions with the "Grand Exchange," selling items if necessary.
     * - FIND_GE: Finds and moves to the nearest "Grand Exchange" desk for item transactions, transitioning to the GE state on success.
     */
    override fun tick() {
        ticks++
        if (ticks++ >= 800) {
            ticks = 0
            refresh()
            return
        }


        if (ticks % 30 == 0) {
            for ((zone, resolution) in common_stuck_locations) {
                if (zone.insideBorder(bot)) {
                    resolution(this)
                    return
                }
            }
        }

        when (state) {

            State.LOOT_DELAY -> {
                bot!!.pulseManager.run(object : Pulse() {
                    /**
                     * A variable that acts as a counter.
                     *
                     * It is initialized to 0 and can be incremented or decremented
                     * as needed to keep track of countable events or items.
                     */
                    var counter1 = 0
                    /**
                     * This method increments the counter1 and checks its value to determine the state of an object.
                     * When counter1 reaches 7, it sets the state to `Adventurer.State.LOOT` and returns true.
                     * Otherwise, it returns false.
                     *
                     * @return true if counter1 is 7 and the state is set to LOOT, otherwise false
                     */
                    override fun pulse(): Boolean {
                        when (counter1++) {
                            7 -> return true.also { state = State.LOOT }
                        }
                        return false
                    }
                })
            }

            State.LOOT -> {
                val items = AIRepository.groundItems[bot]
                if (items?.isNotEmpty() == true && !bot!!.inventory.isFull) {
                    items.toTypedArray().forEach {
                        scriptAPI!!.takeNearestGroundItem(it.id)
                    }
                    return
                } else {
                    state = State.EXPLORE
                }
            }

            State.START -> {
                if (freshspawn) {
                    freshspawn = false
                    scriptAPI!!.randomWalkTo(lumbridge, 20)
                } else {
                    city = getRandomCity()
                    state = State.TELEPORTING
                }
            }

            State.TELEPORTING -> {
                ticks = 0
                counter = 0
                if (bot!!.location != city) {
                    poi = false
                    scriptAPI!!.teleport(city)
                } else {
                    poi = false
                    state = State.EXPLORE
                }
            }

            State.EXPLORE -> {
                if (counter++ == 350) {
                    state = State.TELEPORTING
                }

                val chance = if (city == ge || city == ge2) 5000 else 2500
                if (RandomFunction.random(chance) <= 10) {
                    val nearbyPlayers = RegionManager.getLocalPlayers(bot!!)
                    if (nearbyPlayers.isNotEmpty()) {
                        ticks = 0
                        dialogue()
                    }
                }

                if (RandomFunction.random(1000) <= 150 && !poi) {
                    val roamDistance = if (city != ge && city != ge2) 225 else 7
                    if ((city == ge || city == ge2) && RandomFunction.random(100) < 90) {
                        if (!bot!!.bank.isEmpty) {
                            state = State.FIND_GE
                        }
                        return
                    }
                    scriptAPI!!.randomWalkTo(city, roamDistance)
                    return
                }

                if (RandomFunction.random(1000) <= 50 && poi) {
                    val roamDistancePoi = when (poiloc) {
                        teakfarm, crawlinghands -> 5
                        treegnome -> 50
                        isafdar -> 40
                        eaglespeek -> 40
                        keldagrimout -> 30
                        teak1 -> 30
                        miningguild -> 5
                        magics, coal -> 7
                        gemrocks, chaosnpc, chaosnpc2 -> 1
                        else -> 60
                    }
                    scriptAPI!!.randomWalkTo(poiloc, roamDistancePoi)
                    return
                }

                if (RandomFunction.random(1000) <= 75) {
                    if (city != ge && city != ge2) {
                        immerse()
                        return
                    } else {
                        return
                    }
                }

                if (RandomFunction.random(20000) <= 60 && !poi) {
                    poiloc = getRandomPoi()
                    city = teak1
                    poi = true
                    scriptAPI!!.teleport(poiloc)
                    return
                }

                if ((city == ge || city == ge2) && RandomFunction.random(1000) >= 999) {
                    ticks = 0
                    city = getRandomCity()
                    state = State.TELEPORTING
                }

                if (city == ge || city == ge2) {
                    return
                }

                if (city == teak1 && counter++ >= 240) {
                    city = getRandomCity()
                    state = State.TELEPORTING
                }

                if (counter++ >= 240 && RandomFunction.random(100) >= 10) {
                    city = getRandomCity()
                    if (RandomFunction.random(100) % 2 == 0) {
                        counter = 0
                        ticks = 0
                        state = State.TELEPORTING
                    } else {
                        if (citygroupA.contains(city)) {
                            city = citygroupA.random()
                        } else {
                            city = citygroupB.random()
                        }
                        counter = 0
                        ticks = 0
                        state = State.FIND_CITY
                    }
                    counter = 0
                    return
                }
                return
            }

            State.GE -> {
                var ge = false
                if (counter++ == 180) {
                    state = State.TELEPORTING
                }
                if (!sold) {
                    if (counter++ >= 15) {
                        sold = true
                        ge = true
                        counter = 0
                        ticks = 0
                        scriptAPI!!.sellAllOnGeAdv()
                        return
                    }
                } else if (ge && sold) {
                    ge = false
                    city = getRandomCity()
                    state = State.TELEPORTING
                }
                return
            }

            State.FIND_GE -> {
                if (counter++ == 180) {
                    state = State.TELEPORTING
                }
                sold = false
                val ge: Scenery? = scriptAPI!!.getNearestNode("Desk", true) as Scenery?
                if (ge == null || bot!!.bank.isEmpty) state = State.EXPLORE
                class GEPulse : MovementPulse(bot!!, ge, DestinationFlag.OBJECT) {
                    /**
                     * This method executes a series of actions to update the state of the bot.
                     * It updates the bot's face location based on the Grand Exchange location
                     * and sets the state of the bot to GE.
                     *
                     * @return Boolean indicating the successful execution of these actions.
                     */
                    override fun pulse(): Boolean {
                        bot!!.faceLocation(ge?.location)
                        state = State.GE
                        return true
                    }
                }
                if (ge != null && !bot!!.bank.isEmpty) {
                    counter = 0
                    scriptAPI!!.randomWalkTo(geloc, 3)
                    GameWorld.Pulser.submit(GEPulse())
                }
                return
            }

            State.FIND_BANK -> {
                if (counter++ == 300) {
                    state = State.TELEPORTING
                }
                val bank: Scenery? = scriptAPI!!.getNearestNode("Bank booth", true) as Scenery?
                if (badedge.insideBorder(bot) || bot!!.location == badedge2 || bot!!.location == badedge3 || bot!!.location == badedge4) {
                    bot!!.randomWalk(5, 5)
                }
                if (bank == null) state = State.EXPLORE
                class BankingPulse : MovementPulse(bot!!, bank, DestinationFlag.OBJECT) {
                    /**
                     * Executes a pulse to update the bot's state and facing direction.
                     *
                     * The bot will face towards the bank's location and change its state to IDLE_BANKS.
                     *
                     * @return Always returns true to indicate the pulse action was executed.
                     */
                    override fun pulse(): Boolean {
                        bot!!.faceLocation(bank?.location)
                        state = State.IDLE_BANKS
                        return true
                    }
                }
                if (bank != null) {
                    bot!!.pulseManager.run(BankingPulse())
                }
                return
            }

            State.IDLE_BANKS -> {
                if (counter++ == 300) {
                    state = State.TELEPORTING
                }
                if (RandomFunction.random(1000) < 100) {
                    for (item in bot!!.inventory.toArray()) {
                        item ?: continue
                        when (item.id) {
                            1359, 590, 1271, 995 -> continue
                        }
                        bot!!.bank.add(item)
                        bot!!.inventory.remove(item)
                    }
                    counter = 0
                    state = State.EXPLORE
                }
                return
            }

            State.FIND_CITY -> {
                if (counter++ >= 600 || (city == ge || city == ge2)) {
                    counter = 0
                    scriptAPI!!.teleport(getRandomCity().also { city = it })
                    state = State.EXPLORE
                }
                if (bot!!.location.equals(city)) {
                    state = State.EXPLORE
                } else {
                    scriptAPI!!.randomWalkTo(city, 5)
                }
                return
            }

            State.IDLE_CITY -> {
                if (counter++ == 300) {
                    state = State.TELEPORTING
                }
                var random = (120..300).random()
                if (counter++ == random && RandomFunction.random(1000) % 33 == 0) {
                    counter = 0
                    state = State.EXPLORE
                }
                return
            }
        }

    }

    /**
     * Generates and sends a chat message based on seasonal events and randomly selected dialogue lines.
     *
     * This method chooses a local player's username and uses the current date to determine any applicable
     * holiday or event. Depending*/
    fun dialogue() {
        val localPlayer = RegionManager.getLocalPlayers(bot!!).random()
        val until = 1225 - dateCode
        val lineStd = dialogue.getLines("standard").rand()
        var lineAlt = ""

        when {
            //Celebrates Halloween!
            dateCode == 1031 -> lineAlt = dialogue.getLines("halloween").rand()

            //Celebrates lead up to Christmas!
            until in 2..23 -> lineAlt = dialogue.getLines("approaching_christmas").rand()

            //Celebrates Christmas Day!
            dateCode == 1225 -> lineAlt = dialogue.getLines("christmas_day").rand()

            //Celebrates Christmas Eve
            dateCode == 1224 -> lineAlt = dialogue.getLines("christmas_eve").rand()

            //New years eve
            dateCode == 1231 -> lineAlt = dialogue.getLines("new_years_eve").rand()

            //New years
            dateCode == 101 -> lineAlt = dialogue.getLines("new_years").rand()

            //Valentines
            dateCode == 214 -> lineAlt = dialogue.getLines("valentines").rand()

            //Easter
            dateCode == 404 -> lineAlt = dialogue.getLines("easter").rand()
        }

        val chat = if (lineAlt.isNotEmpty() && Random.nextBoolean()) {
            lineAlt
        } else {
            lineStd
        }.replace("@name", localPlayer.username).replace("@timer", until.toString())

        scriptAPI!!.sendChat(chat)
    }

    /**
     * Enum class representing various states in a given workflow or process.
     *
     * The states provide a way to manage and track the progress of operations
     * through defined phases, each of which may have specific roles or associated logic.
     */
    enum class State {
        /**
         * The START class represents the beginning phase of a sequence or process.
         *
         * This class can be used to initialize or set up necessary conditions before an operation
         * or workflow commences. It may contain initialization logic that prepares the system or
         * environment for subsequent actions.
         *
         * The START class does not contain specific methods or properties by itself but serves as a
         * representational entity within a larger context where initial setup or configuration is required.
         */
        START, /**
         * The EXPLORE class handles various exploration tasks within a defined environment.
         *
         * This class provides methods to initialize exploration parameters, execute the
         * exploration process, analyze findings, and present results. It's designed to
         * be flexible to accommodate different types of explorations, whether they are
         * geographical, data-related, or involve other contexts.
         *
         * Responsibilities:
         * - Configure initial exploration parameters such as starting point, scope,
         *   and criteria for exploration.
         * - Execute the exploration process, tracking progress and collecting relevant
         *   data along the way.
         * - Analyze the collected data to identify significant findings or patterns.
         * - Generate and present a summary of the exploration results.
         *
         * Ensure that all necessary preconditions are met before starting the exploration
         * process, such as correctly initialized parameters and adequate resources.
         *
         * Methods in this class should handle errors gracefully, providing meaningful
         * feedback to diagnose issues during the exploration.
         *
         * Potential applications include:
         * - Geographic information systems (GIS) for terrain or route exploration.
         * - Data mining tasks that involve sifting through large datasets to find trends.
         * - Autonomous robotic systems that need to navigate unfamiliar environments*/
        EXPLORE, /**
         * Enum class representing different bank finding algorithms.
         *
         * This enum provides different constants to represent the various
         * algorithms that can be used to locate a bank based on specific criteria.
         * The available constants may include methods such as using a name search,
         * using geographical data, or other proprietary algorithms.
         *
         * Example constants:
         * - NAME_SEARCH: Find a bank by its name.
         * - GEO_LOCATION: Find a bank by its geographical coordinates.
         * - BANK_CODE: Find a bank by its unique banking code.
         *
         * Each constant in this enum encapsulates a specific algorithm used
         * for finding a bank.
         */
        FIND_BANK, /**
         * Enum class that represents different states of bank activity.
         *
         * This class is used in scenarios where the status of bank operations needs to be tracked.
         * The states can be used to indicate whether a bank is currently idle or active.
         *
         * The enum values should be used with caution and should reflect the actual state of bank activity.
         */
        IDLE_BANKS, /**
         * Enum class FIND_CITY
         *
         * Represents various constants used for indicating actions related to finding a city.
         *
         * Enums:
         * - FIND: Represents the action to find a city.
         * - LOCATE: Represents the action to locate a city on a map.
         * - DISPLAY: Represents the action to display information about a city.
         */
        FIND_CITY, /**
         * IDLE_CITY is an enumeration that represents the idle state of a city in the context of a simulation or game.
         *
         * This enumeration can be used to define the specific state where a city is not active or performing any operations.
         * It can help manage and track the status of cities within a simulation, making it easier to handle different city behaviors.
         *
         * The IDLE_CITY state can be particularly useful for scenarios such as:
         * - Pausing city activities when no resources are available.
         * - Indicating a transitional state between city activities.
         * - Managing city resources effectively by putting the city in an idle state when not in use.
         *
         * Note that this enumeration is part of a larger set of states that define various behaviors and activities of a city.
         */
        IDLE_CITY, /**
         * Represents a general entity (GE) within the system.
         *
         * This class serves as a base class for various entities
         * that may have common properties or methods. It is designed
         * to be extended by more specific entity classes.
         *
         * Properties:
         * - id: A unique identifier for the entity.
         * - name: The name of the entity.
         *
         * Functions:
         * - validate(): Validates the entity's properties.
         * - save(): Persists the entity to a data store.
         */
        GE, /**
         * The `TELEPORTING` class represents a mode of transportation in*/
        TELEPORTING, /**
         * A class representing a collection of items that can be acquired, commonly known as loot.
         *
         * This class provides methods to manage and manipulate a collection of loot items.
         * It allows adding items to the loot, removing items from the loot, and retrieving
         * the list of current loot items.
         *
         * @property items The list of items currently in the loot.
         * @constructor Creates an empty collection of loot items.
         */
        LOOT, /**
         * Enum class representing different delays for loot appearance in a game.
         *
         * This enum provides predefined delay durations associated with the loot appearance after certain events or actions within a game environment.
         * It can be used to manage timed events and ensure a consistent experience for users interacting with loot mechanics.
         *
         * Each constant represents a unique delay period, suitable for various gameplay scenarios.
         */
        LOOT_DELAY, /**
         * This class provides functionality to find the first element in a sorted list that is greater than or equal to a given value.
         *
         * The class leverages binary search techniques to achieve an efficient search process.
         *
         * @constructor Creates an instance of the FIND_GE class.
         *
         **/
        FIND_GE
    }


    /**
     * Creates a new instance of the Script class.
     *
     * The method initializes a new Adventurer script with the provided style
     * and sets its initial state to START. Depending on the type of the bot,
     * it either configures a ranged or melee adventurer bot with a medium tier.
     *
     * @return a new Script instance with the initialized Adventurer bot.
     */
    override fun newInstance(): Script {
        val script = core.game.bots.impl.Adventurer(style)
        script.state = State.START
        val tier = CombatBotAssembler.Tier.MED
        if (type == CombatBotAssembler.Type.RANGE) script.bot =
            CombatBotAssembler().RangeAdventurer(tier, bot!!.startLocation)
        else script.bot = CombatBotAssembler().MeleeAdventurer(tier, bot!!.startLocation)
        return script
    }

    /**
     * Companion object containing predefined locations, zone borders,
     * and groups of locations used in various parts of the application.
     */
    companion object {
        /**
         * `badedge` represents a specific region defined by its border coordinates.
         *
         * ZoneBorders constructor parameters:
         * - 3094: The x-coordinate of the starting (lower-left) corner.
         * - 3494: The y-coordinate of the starting (lower-left) corner.
         * - 3096: The x-coordinate of the ending (upper-right) corner.
         * - 3497: The y-coordinate of the ending (upper-right) corner.
         */
        val badedge = ZoneBorders(3094, 3494, 3096, 3497)
        /**
         * Represents a specific location on the game map identified
         * by its coordinates (x: 3094, y: 3492) and a height level of 0.
         * The variable name 'badedge2' suggests it might denote a boundary or
         * edge location with potentially problematic features or special significance.
         *
         * This constant can be used to reference this particular location
         * throughout the game code, ensuring consistency and readability.
         */
        val badedge2 = Location.create(3094, 3492, 0)
        /**
         * Represents a specific location coordinate in a 3-dimensional space.
         * The coordinate is set to (x=3094, y=3490, z=0).
         *
         * This variable might be used to reference a predetermined location or
         * point of interest within a given map or spatial configuration.
         */
        val badedge3 = Location.create(3094, 3490, 0)
        /**
         * Represents a specific coordinate location in a two-dimensional space.
         * The badedge4 variable holds the coordinates (3094, 3494) at level 0.
         * This location might be used within the context of a mapping or positioning system.
         */
        val badedge4 = Location.create(3094, 3494, 0)

        /**
         * Represents the location of Yanille in the game.
         *
         * The coordinates for Yanille are (2615, 3104) on the 0th plane (ground level).
         *
         * This variable can be used to reference the specific location of Yanille
         * within the game's world map for various in-game interactions or functionalities
         * requiring positional data.
         */
        val yanille: Location = Location.create(2615, 3104, 0)
        /**
         * Represents the coordinates of the*/
        val ardougne: Location = Location.create(2662, 3304, 0)
        /**
         * Represents the coordinates of a location named "Seers" in the game.
         * The location is defined by its x, y coordinates and a plane level.
         * This variable is immutable and should be used to reference the Seers'
         * settlement coordinates within the application.
         *
         * @property x The x-coordinate of the Seers location.
         * @property y The y-coordinate of the Seers location.
         * @property plane The plane or level of the Seers location.
         */
        val seers: Location = Location.create(2726, 3485, 0)
        /**
         * Represents the in-game location of Edgeville*/
        val edgeville: Location = Location.create(3088, 3486, 0)
        /**
         * Represents a geographical location using specific coordinates.
         *
         * This variable defines a fixed location with the following coordinates:
         * - X-coordinate: 3168
         * - Y-coordinate: 3487
         * - Z-coordinate: 0, which often denotes ground level or default elevation.
         *
         * Typically used in geographical or spatial computations where exact positioning is required.
         */
        val ge: Location = Location.create(3168, 3487, 0)
        /**
         * Represents a specific location with coordinates (3161, 3493) and a level of 0.
         *
         * This variable is used to pinpoint a location within the given coordinate system.
         * It can be used for map-related functionalities or location-based operations where
         * having predefined key points is necessary.
         *
         * The Location object encapsulates the details of the coordinates and the level,
         * providing an easy reference for this specific geolocation.
         *
         * @see Location
         */
        val ge2: Location = Location.create(3161, 3493, 0)
        /**
         * Represents the location coordinates for the town of Catherby.
         * The location is created with x-coordinate 2809, y-coordinate 3435,
         * and on the*/
        val catherby: Location = Location.create(2809, 3435, 0)
        /**
         * Represents the coordinates of the city Falador in the game world.
         * Initialized to the default center position at coordinates (2965, 3380) on the ground level (Z-axis = 0).
         */
        val falador: Location = Location.create(2965, 3380, 0)
        /**
         * Location of Varrock, a prominent city in the game world.
         *
         * This constant holds the coordinates for Varrock, providing
         * convenient access to this location across the application.
         */
        val varrock: Location = Location.create(3213, 3428, 0)
        /**
         * Represents the location of Draynor within the game world.
         *
         * This location object is defined with fixed coordinates:
         * x = 3080, y = 3250, z = 0.
         *
         * It is used to mark the specific point known as Draynor.
         */
        val draynor: Location = Location.create(3080, 3250, 0)
        /**
         * Represents the geographical coordinates of the town Rimmington.
         *
         * This location in the virtual world is defined by its X and Y coordinates,
         * placing it specifically at (2977, 3239) on the standard plane (z-coordinate 0).
         * It is often used for referencing Rimmington in game map or navigation systems.
         */
        val rimmington: Location = Location.create(2977, 3239, 0)
        /**
         * Represents the in-game location of Lumbridge.
         *
         * This variable holds a `Location` object that specifies the coordinates and plane of
         * the Lumbridge area in the game.
         *
         * Coordinates: (x=3222, y=3219)
         * Plane/Level: 0 (ground level)
         */
        val lumbridge: Location = Location.create(3222, 3219, 0)
        /**
         * Represents the specific geographical location of Karamja in the game's world map.
         * This location is identified by its X coordinate (2849), Y coordinate (3033), and Z coordinate (0).
         * Typically used to reference or teleport to Karamja within the game.
         */
        val karamja = Location.create(2849, 3033, 0)
        /**
         * The `alkharid` variable represents the location coordinates for Al Kharid city
         * in the game world. The coordinates are specified as (X: 3297, Y: 3219, Z: 0),
         * where X and Y define the position on the 2D plane, and Z defines the plane level.
         */
        val alkharid = Location.create(3297, 3219, 0)
        /**
         * The default location for the character Feldiphills.
         *
         * This variable holds the coordinates representing the starting
         * position or a specific significant location associated with*/
        val feldiphills = Location.create(2535, 2919, 0)
        /**
         * Represents a specific geographical location by initializing a `Location` object with
         * the specified coordinates (2241, 3217) and level (0). The coordinates match a particular
         * area referred to as "isafdar" within the context of the application.
         *
         * The `Location` object encapsulates the x, y coordinates, and z level, which describes a
         * particular point in a given space or map. This can be used for navigation, mapping, and
         * location-based functionalities within the software.
         */
        val isafdar = Location.create(2241, 3217, 0)
        /**
         * Represents the location of Eagles' Peak in a grid-based coordinate system.
         * This variable is initialized with coordinates (2333, 3579) at ground level (0).
         *
         * Coordinates:
         *  - X: 2333
         *  - Y: 3579
         *  - Z: 0
         */
        val eaglespeek = Location.create(2333, 3579, 0)
        /**
         * The constant `canafis` represents a specific location in a virtual coordinate system.
         * The coordinates (3492, 3485) refer to the x and y positions respectively,
         * while the final parameter (0) indicates the level or height in the environment.
         */
        val canafis = Location.create(3492, 3485, 0)
        /**
         * Represents the in-game location of the `treegnome`, a specific point in a game world.
         * The coordinates are*/
        val treegnome = Location.create(2437, 3441, 0)
        /**
         * Represents a specific location with the given coordinates and plane value.
         *
         * This variable is initialized using the `Location.create` method with coordinates
         * (x: 2334, y: 3048) on the plane 0.
         */
        val teak1 = Location.create(2334, 3048, 0)
        /**
         * A constant representing the location of the Teak tree farm in the game world.
         * This location is defined by the coordinates (2825, 3085) on the default plane (0).
         * The Teak tree farm is a specific area where players can chop Teak trees for wood.
         */
        val teakfarm = Location.create(2825, 3085, 0)
        /**
         * Represents a location defined within a coordinate system with the specific
         * coordinates (x: 2724, y: 3692, z: 0). This location is presumably related to
         * the area or entity named Keldagrim, which may have specific relevance within
         * the context of the application or game. The coordinates indicate a fixed
         * position, and the 'z' coordinate typically denotes elevation or layer within
         * a multi-layered coordinate system.
         */
        val keldagrimout = Location.create(2724, 3692, 0)
        /**
         * Represents the coordinates of the Mining Guild's entry point in the game.
         * The Mining Guild is a designated area for mining activities, typically providing rich ore veins and resources.
         * This variable specifies the default location where players can enter the Mining Guild.
         */
        val miningguild = Location.create(3046, 9740, 0)
        /**
         * The default coordinates for the 'magics' location.
         * This variable represents a specific point in a coordinate system with x, y, and z values,
         * indicating latitude, longitude, and altitude/elevation respectively.
         */
        val magics = Location.create(2285, 3146, 0)
        /**
         * Represents the specific geographical coordinates of a coal location.
         * These coordinates are used within the context of the application
         * to identify and work with a known location where coal can be found.
         * The coordinates include X, Y, and Z values: (2581, 3481, 0).
         */
        val coal = Location.create(2581, 3481, 0)
        /**
         * Represents the location coordinates for the crawling hands.
         * The coordinates are specified as (x=3422, y=3548) on the ground level (z=0).
         * Typically used to mark a specific location within a grid or map.
         */
        val crawlinghands = Location.create(3422, 3548, 0)
        /**
         * Represents the location of gem rocks in the game world.
         * The gem rocks are located at coordinates (x: 2825, y: 2997) on the 0th plane.
         */
        val gemrocks = Location.create(2825, 2997, 0)
        /**
         * `chaosnpc` is a Location object initialized with coordinates (2612, 9484)
         * and a plane level of 0. This location likely represents a specific point
         * in a game or simulation, potentially associated with a non-player character (NPC)
         * named "Chaos."
         */
        val chaosnpc = Location.create(2612, 9484, 0)
        /**
         * Represents the location with coordinates (2580, 9501) at ground level (z = 0).
         *
         * This variable is typically used to denote the starting position or a significant
         */
        val chaosnpc2 = Location.create(2580, 9501, 0)
        /**
         *
         */
        val taverly = Location.create(2909, 3436, 0)
        /**
         * Represents a list of cities grouped under 'citygroupA'.
         * Contains predefined city locations involving:
         * - Falador
         * - Varrock
         * - Draynor
         * - Rimmington
         * - Lumbridge
         * - Edgeville
         */
        var citygroupA = listOf(
            falador,
            varrock,
            draynor,
            rimmington,
            lumbridge,
            edgeville
        )
        /**
         * A list of city groups represented by Adventurer companion objects.
         * This variable groups cities such as Yanille, Ardougne, Seers', and Catherby.
         * It is used to organize or categorize game locations.
         */
        var citygroupB = listOf(
            yanille,
            ardougne,
            seers,
            catherby
        )

        /**
         *
         */
        var bankMap = mapOf<Location, ZoneBorders>(
            falador to ZoneBorders(2950, 3374, 2943, 3368),
            varrock to ZoneBorders(3182, 3435, 3189, 3446),
            draynor to ZoneBorders(3092, 3240, 3095, 3246),
            edgeville to ZoneBorders(3093, 3498, 3092, 3489),
            yanille to ZoneBorders(2610, 3089, 2613, 3095),
            ardougne to ZoneBorders(2649, 3281, 2655, 3286),
            seers to ZoneBorders(2729, 3493, 2722, 3490),
            catherby to ZoneBorders(2807, 3438, 2811, 3441)
        )

        /**
         * A list of predefined city instances used by the Adventurer bot.
         *
         * This list contains various locations including:
         * - Yanille
         * - Ardougne
         * - Seers
         * - Catherby
         * - Falador
         * - Varrock
         * - Draynor
         * - Rimmington
         * - Lumbridge
         * - Grand Exchange (GE)
         * - Second Grand Exchange (GE2)
         * - Edgeville
         *
         * These locations are used within the adventure game bot for navigation and tasks.
         */
        val cities = listOf(
            yanille,
            ardougne,
            seers,
            catherby,
            falador,
            varrock,
            draynor,
            rimmington,
            lumbridge,
            ge,
            ge2,
            edgeville
        )

        /**
         * List of predefined points of interest (POIs) represented by instances of the Adventurer class.
         * These POIs include various locations such as Karamja, Al Kharid, Feldip Hills, and more.
         *
         * The list includes duplicated entries for certain POIs indicating multiple instances of the same location.
         */
        val pois = listOf(
            karamja,
            karamja,
            alkharid,
            alkharid,
            feldiphills,
            feldiphills,
            isafdar,
            eaglespeek,
            eaglespeek,
            canafis,
            treegnome,
            treegnome,
            teak1,
            teakfarm,
            keldagrimout,
            miningguild,
            coal,
            crawlinghands,
            magics,
            gemrocks,
            chaosnpc,
            chaosnpc,
            chaosnpc2,
            taverly
        )

        /**
         * Represents the top location of White Wolf Mountain within the game world.
         * This location is defined by its coordinates (x: 2850, y: 3496) at ground level (z: 0).
         */
        private val whiteWolfMountainTop = Location(2850, 3496, 0)
        /**
         * An array of `Location` objects representing the path from Catherby to the top of White Wolf Mountain.
         *
         * The path includes:
         * - Starting at coordinates (2856, 3442, 0)
         * - Proceeding to coordinates (2848, 3455, 0)
         * - Moving to coordinates (2848, 3471, 0)
         * - Ending at coordinates (2848, 3487, 0)
         */
        private val catherbyToTopOfWhiteWolf =
            arrayOf(Location(2856, 3442, 0), Location(2848, 3455, 0), Location(2848, 3471, 0), Location(2848, 3487, 0))
        /**
         * An array of `Location` objects representing a path from Tavlery to the top of White Wolf.
         */
        private val tavleryToTopOfWhiteWolf = arrayOf(
            Location(2872, 3425, 0),
            Location(2863, 3440, 0),
            Location(2863, 3459, 0),
            Location(2854, 3475, 0),
            Location(2859, 3488, 0)
        )
        /**
         * A mapping of specific in-game ZoneBorders to navigation or interaction logic
         * designed to resolve situations where an Adventurer bot gets "stuck" in various
         * known problematic locations. Each ZoneBorders key represents a specific
         * in-game area, and the associated lambda function defines the corrective
         * actions to take when an Adventurer is detected within that area.
         */
        val common_stuck_locations = mapOf(
            // South of Tavlery dungeon
            ZoneBorders(2878, 3386, 2884, 3395) to { it: Adventurer ->
                it.scriptAPI!!.walkArray(tavleryToTopOfWhiteWolf + whiteWolfMountainTop + catherbyToTopOfWhiteWolf.reversedArray())
            },
            // West of Tavlery dungeon
            ZoneBorders(2874, 3390, 2880, 3401) to { it: Adventurer ->
                it.scriptAPI!!.walkArray(tavleryToTopOfWhiteWolf + whiteWolfMountainTop + catherbyToTopOfWhiteWolf.reversedArray())
            },
            // South of White Wolf Mountain in Tavlery
            ZoneBorders(2865, 3408, 2874, 3423) to { it: Adventurer ->
                it.scriptAPI!!.walkArray(tavleryToTopOfWhiteWolf + whiteWolfMountainTop + catherbyToTopOfWhiteWolf.reversedArray())
            },
            // On beginning of White Wolf Mountain in Tavlery
            ZoneBorders(2855, 3454, 2852, 3450) to { it: Adventurer ->
                it.scriptAPI!!.walkArray(tavleryToTopOfWhiteWolf + whiteWolfMountainTop + catherbyToTopOfWhiteWolf.reversedArray())
            },
            // South of White Wolf Mountain in Catherby
            ZoneBorders(2861, 3425, 2867, 3432) to { it: core.game.bots.impl.Adventurer ->
                it.scriptAPI!!.walkArray(catherbyToTopOfWhiteWolf + whiteWolfMountainTop + tavleryToTopOfWhiteWolf.reversedArray())
            },
            // On beginning of White Wolf Mountain in Catherby
            ZoneBorders(2863, 3441, 2859, 3438) to { it: core.game.bots.impl.Adventurer ->
                it.scriptAPI!!.walkArray(catherbyToTopOfWhiteWolf + whiteWolfMountainTop + tavleryToTopOfWhiteWolf.reversedArray())
            },
            // At the Crumbling Wall in Falador
            ZoneBorders(2937, 3356, 2936, 3353) to { it: core.game.bots.impl.Adventurer ->
                // Interact with the Crumbling Wall
                val wall = it.scriptAPI!!.getNearestNode("Crumbling wall", true)
                if (wall == null) {
                    it.refresh()
                    it.ticks = 0
                    return@to
                }
                it.scriptAPI!!.interact(it.bot!!, wall, "Climb-over")
            },
            // Northwest corner of Draynor Bank
            ZoneBorders(3092, 3246, 3091, 3247) to { it: core.game.bots.impl.Adventurer ->
                // Walk into Draynor Bank
                it.scriptAPI!!.walkTo(Location(3093, 3243, 0))
            },
            // West of GE, stuck in the corner south of the outlaw place
            ZoneBorders(3140, 3468, 3140, 3468) to { it: core.game.bots.impl.Adventurer ->
                // Walk to Barbarian village
                it.scriptAPI!!.walkArray(
                    arrayOf(
                        Location.create(3135, 3516, 0),
                        Location.create(3103, 3489, 0),
                        Location.create(3082, 3423, 0)
                    )
                )
            },
        )

        /**
         * A JSON object representing a dialogue.
         *
         * The dialogue object may contain key-value pairs that define the structure and content of a dialogue for a conversation system.
         * It could include fields such as participants, messages, timestamps, and additional metadata relevant to the dialogue instance.
         */
        val dialogue: JSONObject
        /**
         * Represents a unique integer code generated based on the current date.
         * This integer is often used for timestamping and versioning purposes within the application.
         */
        val dateCode: Int

        init {
            val reader = FileReader(Configuration.BOT_DATA_PATH + File.separator + "bot_dialogue.json")
            val parser = org.json.simple.parser.JSONParser()
            val data = parser.parse(reader) as JSONObject

            dialogue = data

            val formatter = DateTimeFormatter.ofPattern("MMdd")
            val current = LocalDateTime.now()
            val formatted: String = current.format(formatter)
            dateCode = formatted.toInt()
        }

        /**
         * Fetches an array of lines corresponding to the specified category from the JSON object.
         *
         * @param category The key in the JSON object whose associated value is a JSONArray.
         * @return A JSONArray containing the lines corresponding to the specified category.
         */
        private fun JSONObject.getLines(category: String): JSONArray {
            return this[category] as JSONArray
        }

        /**
         * Returns a random element from the JSONArray as a String.
         *
         * @return A randomly selected element from the JSONArray.
         */
        private fun JSONArray.rand(): String {
            return this.random() as String
        }
    }
}
