package content.global.skill.runecrafting.runes

import core.game.node.entity.combat.spell.Runes
import core.game.node.item.Item

/**
 * Represents runes in the game.
 *
 * @property rune       The item associated with the rune.
 * @property level      The level required to craft the rune.
 * @property experience The experience gained from crafting the rune.
 * @property multiple   The levels at which multiple runes can be crafted.
 */
enum class Rune(val rune: Item, val level: Int, val experience: Double, private vararg val multiple: Int) {
    AIR(
        rune = Runes.AIR_RUNE.transform(),
        level = 1,
        experience = 5.0,
        multiple = intArrayOf(1, 11, 22, 33, 44, 55, 66, 77, 88, 99, 110)
    ),
    MIND(
        rune = Runes.MIND_RUNE.transform(),
        level = 2,
        experience = 5.5,
        multiple = intArrayOf(1, 14, 28, 42, 56, 70, 84, 98, 112)
    ),
    WATER(
        rune = Runes.WATER_RUNE.transform(),
        level = 5,
        experience = 6.0,
        multiple = intArrayOf(1, 19, 38, 57, 76, 95, 114)
    ),
    EARTH(
        rune = Runes.EARTH_RUNE.transform(),
        level = 9,
        experience = 6.5,
        multiple = intArrayOf(1, 26, 52, 78, 104)
    ),
    FIRE(
        rune = Runes.FIRE_RUNE.transform(),
        level = 14,
        experience = 7.0,
        multiple = intArrayOf(1, 35, 70, 105)
    ),
    BODY(
        rune = Runes.BODY_RUNE.transform(),
        level = 20,
        experience = 7.5,
        multiple = intArrayOf(1, 46, 92, 138)
    ),
    COSMIC(
        rune = Runes.COSMIC_RUNE.transform(),
        level = 27,
        experience = 8.0,
        multiple = intArrayOf(1, 59, 118)
    ),
    CHAOS(
        rune = Runes.CHAOS_RUNE.transform(),
        level = 35,
        experience = 8.5,
        multiple = intArrayOf(1, 74, 148)
    ),
    ASTRAL(
        rune = Runes.ASTRAL_RUNE.transform(),
        level = 40,
        experience = 8.7,
        multiple = intArrayOf(1, 82, 164)
    ),
    NATURE(
        rune = Runes.NATURE_RUNE.transform(),
        level = 44,
        experience = 9.0,
        multiple = intArrayOf(1, 91, 182)
    ),
    LAW(
        rune = Runes.LAW_RUNE.transform(),
        level = 54,
        experience = 9.5,
        multiple = intArrayOf(1, 110)
    ),
    DEATH(
        rune = Runes.DEATH_RUNE.transform(),
        level = 65,
        experience = 10.0,
        multiple = intArrayOf(1, 131)
    ),
    BLOOD(
        rune = Runes.BLOOD_RUNE.transform(),
        level = 77,
        experience = 10.5,
        multiple = intArrayOf(1, 154)
    ),
    SOUL(
        rune = Runes.SOUL_RUNE.transform(),
        level = 90,
        experience = 11.0
    );


    /**
     * Gets the multiple.
     *
     * @return The multiple.
     */
    fun getMultiple(): IntArray? {
        return multiple
    }

    /**
     * Checks if this rune uses normal essence.
     *
     * @return `true` if so.
     */
    val isNormal: Boolean
        get() = this == AIR || this == MIND || this == WATER || this == EARTH || this == FIRE || this == BODY

    /**
     * Checks if this rune is a multi creation rune.
     *
     * @return `true` if so.
     */
    fun isMultiple(): Boolean {
        return getMultiple() != null
    }

    companion object {
        /**
         * Method used to get the [Rune] by the item.
         * @param item the item.
         * @return the `Rune` or `Null`.
         */
        @JvmStatic
        fun forItem(item: Item): Rune? {
            for (rune in values()) {
                if (rune.rune.id == item.id) {
                    return rune
                }
            }
            return null
        }

        /**
         * Method used to get the [Rune] by the name.
         *
         * @param [name] the name of the rune.
         * @return the corresponding [Rune] or `null` if not found.
         */
        fun forName(name: String): Rune? {
            return values().find { it.name == name }
        }
    }
}
