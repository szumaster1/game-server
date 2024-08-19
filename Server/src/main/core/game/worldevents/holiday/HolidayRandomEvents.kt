package core.game.worldevents.holiday

import core.game.worldevents.holiday.christmas.randoms.*
import core.game.worldevents.holiday.halloween.randoms.*

/**
 * Holiday random events
 * @author Zerken
 *
 * @param npc Represents the non-player character associated with the event
 * @param type Specifies the type of holiday for the event
 * @constructor Initializes a HolidayRandomEvents instance with the given NPC and type
 */
enum class HolidayRandomEvents(val npc: HolidayRandomEventNPC, val type: String) {
    /**
     * Black Cat.
     */
    BLACK_CAT(
        npc = BlackCatHolidayRandomNPC(),
        "halloween"
    ),

    /**
     * Spider.
     */
    SPIDER(
        npc = SpiderHolidayRandomNPC(),
        "halloween"
    ),

    /**
     * Ghost.
     */
    GHOST(
        npc = GhostHolidayRandomNPC(),
        "halloween"
    ),

    /**
     * Zombie.
     */
    ZOMBIE(
        npc = ZombieHolidayRandomNPC(),
        "halloween"
    ),

    /**
     * Witch.
     */
    WITCH(
        npc = WitchHolidayRandomNPC(),
        "halloween"
    ),

    /**
     * Death.
     */
    DEATH(
        npc = DeathHolidayRandomNPC(),
        "halloween"
    ),

    /**
     * Vampire.
     */
    VAMPIRE(
        npc = VampireHolidayRandomNPC(),
        "halloween"
    ),

    /**
     * Choir.
     */
    CHOIR(
        npc = ChoirHolidayRandomNPC(),
        "christmas"
    ),

    /**
     * Santa.
     */
    SANTA(
        npc = SantaHolidayRandomNPC(),
        "christmas"
    ),

    /**
     * Snowman Fight.
     */
    SNOWMAN_FIGHT(
        npc = SnowmanFightHolidayRandom(),
        "christmas"
    ),

    /**
     * Jack Frost.
     */
    JACK_FROST(
        npc = JackFrostHolidayRandomNPC(),
        "christmas"
    ),

    /**
     * Snowman.
     */
    SNOWMAN(
        npc = SnowmanHolidayRandomNPC(),
        "christmas"
    ),

    /**
     * Snowstorm.
     */
    SNOWSTORM(
        npc = SnowStormHolidayRandomNPC(),
        "christmas"
    ),

    /**
     * Cook.
     */
    COOK(
        npc = CookHolidayRandomNPC(),
        "christmas"
    );

    companion object {
        @JvmField
        // List to hold Halloween events
        val halloweenEventsList = ArrayList<HolidayRandomEvents>()
        // List to hold Christmas events
        val christmasEventsList = ArrayList<HolidayRandomEvents>()
        // List of holiday random event IDs
        val holidayRandomIDs = HolidayRandomEvents.values().map { it.npc.id }.toList()

        init {
            // Populate the event lists with corresponding events
            populateMappings()
        }

        /**
         * Retrieves a random holiday event based on the specified type
         *
         * @param type The type of holiday to retrieve an event for
         * @return A random HolidayRandomEvents instance
         * @throws Exception if the event type is invalid
         */
        fun getHolidayRandom(type: String): HolidayRandomEvents {
            return when (type) {
                "halloween" -> halloweenEventsList.random() // Return a random Halloween event
                "christmas" -> christmasEventsList.random() // Return a random Christmas event
                else -> throw Exception("Invalid event type!") // Throw an exception for invalid types
            }
        }

        // Populates the Halloween and Christmas event lists
        private fun populateMappings() {
            for (event in values()) {
                when (event.type) {
                    "halloween" -> halloweenEventsList.add(event) // Add Halloween events to the list
                    "christmas" -> christmasEventsList.add(event) // Add Christmas events to the list
                }
            }
        }
    }
}