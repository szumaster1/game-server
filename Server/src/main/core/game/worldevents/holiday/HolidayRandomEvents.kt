package core.game.worldevents.holiday

import core.game.worldevents.holiday.christmas.randoms.*
import core.game.worldevents.holiday.halloween.randoms.*

/**
 * Holiday random events
 *
 * @param npc
 * @param type
 * @constructor Holiday random events
 */
enum class HolidayRandomEvents(val npc: HolidayRandomEventNPC, val type: String) {
    /**
     * Black Cat
     *
     * @constructor Black Cat
     */
    BLACK_CAT(npc = BlackCatHolidayRandomNPC(), "halloween"),

    /**
     * Spider
     *
     * @constructor Spider
     */
    SPIDER(npc = SpiderHolidayRandomNPC(), "halloween"),

    /**
     * Ghost
     *
     * @constructor Ghost
     */
    GHOST(npc = GhostHolidayRandomNPC(), "halloween"),

    /**
     * Zombie
     *
     * @constructor Zombie
     */
    ZOMBIE(npc = ZombieHolidayRandomNPC(), "halloween"),

    /**
     * Witch
     *
     * @constructor Witch
     */
    WITCH(npc = WitchHolidayRandomNPC(), "halloween"),

    /**
     * Death
     *
     * @constructor Death
     */
    DEATH(npc = DeathHolidayRandomNPC(), "halloween"),

    /**
     * Vampire
     *
     * @constructor Vampire
     */
    VAMPIRE(npc = VampireHolidayRandomNPC(), "halloween"),

    /**
     * Choir
     *
     * @constructor Choir
     */
    CHOIR(npc = ChoirHolidayRandomNPC(), "christmas"),

    /**
     * Santa
     *
     * @constructor Santa
     */
    SANTA(npc = SantaHolidayRandomNPC(), "christmas"),

    /**
     * Snowman Fight
     *
     * @constructor Snowman Fight
     */
    SNOWMAN_FIGHT(npc = SnowmanFightHolidayRandom(), "christmas"),

    /**
     * Jack Frost
     *
     * @constructor Jack Frost
     */
    JACK_FROST(npc = JackFrostHolidayRandomNPC(), "christmas"),

    /**
     * Snowman
     *
     * @constructor Snowman
     */
    SNOWMAN(npc = SnowmanHolidayRandomNPC(), "christmas"),

    /**
     * Snowstorm
     *
     * @constructor Snowstorm
     */
    SNOWSTORM(npc = SnowStormHolidayRandomNPC(), "christmas"),

    /**
     * Cook
     *
     * @constructor Cook
     */
    COOK(npc = CookHolidayRandomNPC(), "christmas");

    companion object {
        @JvmField
        val halloweenEventsList = ArrayList<HolidayRandomEvents>()
        val christmasEventsList = ArrayList<HolidayRandomEvents>()
        val holidayRandomIDs = HolidayRandomEvents.values().map { it.npc.id }.toList()

        init {
            populateMappings()
        }

        fun getHolidayRandom(type: String): HolidayRandomEvents {
            return when (type) {
                "halloween" -> halloweenEventsList.random()
                "christmas" -> christmasEventsList.random()
                else -> throw Exception("Invalid event type!")
            }
        }

        private fun populateMappings() {
            for (event in values()) {
                when (event.type) {
                    "halloween" -> halloweenEventsList.add(event)
                    "christmas" -> christmasEventsList.add(event)
                }
            }
        }
    }
}
