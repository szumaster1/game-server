package content.global.skill.production.runecrafting.data

import core.game.node.entity.combat.spell.Runes
import core.game.node.item.Item

/**
 * Rune
 *
 * @param rune Represents the item associated with the rune.
 * @param level Indicates the level of the rune.
 * @param experience Represents the experience points gained from the rune.
 * @param multiple Allows for multiple values associated with the rune.
 * @constructor Rune Represents a new instance of the Rune enum class.
 */
enum class Rune(
    val rune: Item,
    val level: Int,
    val experience: Double,
    private vararg val multiple: Int
) {
    /**
     * Air
     *
     * @constructor Air
     */
    AIR(Runes.AIR_RUNE.transform(), 1, 5.0, 1, 11, 22, 33, 44, 55, 66, 77, 88, 99, 110),

    /**
     * Mind
     *
     * @constructor Mind
     */
    MIND(Runes.MIND_RUNE.transform(), 2, 5.5, 1, 14, 28, 42, 56, 70, 84, 98, 112),

    /**
     * Water
     *
     * @constructor Water
     */
    WATER(Runes.WATER_RUNE.transform(), 5, 6.0, 1, 19, 38, 57, 76, 95, 114),

    /**
     * Earth
     *
     * @constructor Earth
     */
    EARTH(Runes.EARTH_RUNE.transform(), 9, 6.5, 1, 26, 52, 78, 104),

    /**
     * Fire
     *
     * @constructor Fire
     */
    FIRE(Runes.FIRE_RUNE.transform(), 14, 7.0, 1, 35, 70, 105),

    /**
     * Body
     *
     * @constructor Body
     */
    BODY(Runes.BODY_RUNE.transform(), 20, 7.5, 1, 46, 92, 138),

    /**
     * Cosmic
     *
     * @constructor Cosmic
     */
    COSMIC(Runes.COSMIC_RUNE.transform(), 27, 8.0, 1, 59, 118),

    /**
     * Chaos
     *
     * @constructor Chaos
     */
    CHAOS(Runes.CHAOS_RUNE.transform(), 35, 8.5, 1, 74, 148),

    /**
     * Astral
     *
     * @constructor Astral
     */
    ASTRAL(Runes.ASTRAL_RUNE.transform(), 40, 8.7, 1, 82, 164),

    /**
     * Nature
     *
     * @constructor Nature
     */
    NATURE(Runes.NATURE_RUNE.transform(), 44, 9.0, 1, 91, 182),

    /**
     * Law
     *
     * @constructor Law
     */
    LAW(Runes.LAW_RUNE.transform(), 54, 9.5, 1, 110),

    /**
     * Death
     *
     * @constructor Death
     */
    DEATH(Runes.DEATH_RUNE.transform(), 65, 10.0, 1, 131),

    /**
     * Blood
     *
     * @constructor Blood
     */
    BLOOD(Runes.BLOOD_RUNE.transform(), 77, 10.5, 1, 154);

    /**
     * Get multiple
     *
     * @return
     */
    fun getMultiple(): IntArray? {
        return multiple
    }

    val isNormal: Boolean
        get() = this == AIR || this == MIND || this == WATER || this == EARTH || this == FIRE || this == BODY

    /**
     * Is multiple
     *
     * @return
     */
    fun isMultiple(): Boolean {
        return getMultiple() != null
    }

    companion object {
        @JvmStatic
        fun forItem(item: Item): Talisman? {
            return Talisman.values().find { it.talisman.id == item.id }
        }

        @JvmStatic
        fun forName(name: String): Rune? {
            return values().find { it.name == name }
        }
    }
}
