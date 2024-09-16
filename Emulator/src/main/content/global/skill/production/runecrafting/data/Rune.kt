package content.global.skill.production.runecrafting.data

import core.game.node.entity.combat.spell.Runes
import core.game.node.item.Item

/**
 * Represents the rune.
 *
 * @param rune          the item.
 * @param level         the required level.
 * @param experience    the experience.
 * @param multiple      the multiple values.
 */
enum class Rune(
    val rune: Item,
    val level: Int,
    val experience: Double,
    private vararg val multiple: Int
) {
    /**
     * The air rune.
     */
    AIR(Runes.AIR_RUNE.transform(), 1, 5.0, 1, 11, 22, 33, 44, 55, 66, 77, 88, 99, 110),

    /**
     * The mind rune.
     */
    MIND(Runes.MIND_RUNE.transform(), 2, 5.5, 1, 14, 28, 42, 56, 70, 84, 98, 112),

    /**
     * The water rune.
     */
    WATER(Runes.WATER_RUNE.transform(), 5, 6.0, 1, 19, 38, 57, 76, 95, 114),

    /**
     * The earth rune.
     */
    EARTH(Runes.EARTH_RUNE.transform(), 9, 6.5, 1, 26, 52, 78, 104),

    /**
     * The fire rune.
     */
    FIRE(Runes.FIRE_RUNE.transform(), 14, 7.0, 1, 35, 70, 105),

    /**
     * The body rune.
     */
    BODY(Runes.BODY_RUNE.transform(), 20, 7.5, 1, 46, 92, 138),

    /**
     * The cosmic rune.
     */
    COSMIC(Runes.COSMIC_RUNE.transform(), 27, 8.0, 1, 59, 118),

    /**
     * The chaos rune.
     */
    CHAOS(Runes.CHAOS_RUNE.transform(), 35, 8.5, 1, 74, 148),

    /**
     * The astral rune.
     */
    ASTRAL(Runes.ASTRAL_RUNE.transform(), 40, 8.7, 1, 82, 164),

    /**
     * The nature rune.
     */
    NATURE(Runes.NATURE_RUNE.transform(), 44, 9.0, 1, 91, 182),

    /**
     * The law rune.
     */
    LAW(Runes.LAW_RUNE.transform(), 54, 9.5, 1, 110),

    /**
     * The death rune.
     */
    DEATH(Runes.DEATH_RUNE.transform(), 65, 10.0, 1, 131),

    /**
     * The blood rune.
     */
    BLOOD(Runes.BLOOD_RUNE.transform(), 77, 10.5, 1, 154),

    /**
     * The soul rune.
     */
    SOUL(Runes.SOUL_RUNE.transform(), 90, 11.0);


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
         *
         * @param item the item.
         * @return the [Rune] or `null`.
         */
        @JvmStatic
        fun forItem(item: Item): Talisman? {
            return Talisman.values().find { it.talisman.id == item.id }
        }

        /**
         * Method used to the [Rune] by the name.
         *
         * @param name the name.
         * @return the [Rune] or `null`.
         */
        @JvmStatic
        fun forName(name: String): Rune? {
            return values().find { it.name == name }
        }
    }
}
