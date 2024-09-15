package content.global.skill.production.runecrafting.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the tiara.
 */
enum class Tiara(val item: Item, val experience: Double) {
    /**
     * The air tiara.
     */
    AIR(
        item = Item(Items.AIR_TIARA_5527),
        experience = 25.0
    ),

    /**
     * The mind tiara.
     */
    MIND(
        item = Item(Items.MIND_TIARA_5529),
        experience = 27.5
    ),

    /**
     * The water tiara.
     */
    WATER(
        item = Item(Items.WATER_TIARA_5531),
        experience = 30.0
    ),

    /**
     * The earth tiara.
     */
    EARTH(
        item = Item(Items.EARTH_TIARA_5535),
        experience = 32.5
    ),

    /**
     * The fire tiara.
     */
    FIRE(
        item = Item(Items.FIRE_TIARA_5537),
        experience = 35.0
    ),

    /**
     * The body tiara.
     */
    BODY(
        item = Item(Items.BODY_TIARA_5533),
        experience = 37.5
    ),

    /**
     * The cosmic tiara.
     */
    COSMIC(
        item = Item(Items.COSMIC_TIARA_5539),
        experience = 40.0
    ),

    /**
     * The chaos tiara.
     */
    CHAOS(
        item = Item(Items.CHAOS_TIARA_5543),
        experience = 43.5
    ),

    /**
     * The nature tiara.
     */
    NATURE(
        item = Item(Items.NATURE_TIARA_5541),
        experience = 45.0
    ),

    /**
     * The law tiara.
     */
    LAW(
        item = Item(Items.LAW_TIARA_5545),
        experience = 47.5
    ),

    /**
     * The death tiara.
     */
    DEATH(
        item = Item(Items.DEATH_TIARA_5547),
        experience = 50.0
    ),

    /**
     * The blood tiara.
     */
    BLOOD(
        item = Item(Items.BLOOD_TIARA_5549),
        experience = 52.5
    );

    val talisman: Talisman?
        get() = Talisman.values().find { it.name == name }

    companion object {
        infix fun from(item: Item): Tiara? = values().firstOrNull { it.item == item }

        /**
         * Method used to get the [Tiara] by the item.
         *
         * @param item the item.
         * @return the [Tiara] or `null`.
         */
        @JvmStatic
        fun forItem(item: Item): Tiara? {
            for (tiara in values()) {
                if (tiara.item.id == item.id) {
                    return tiara
                }
            }
            return null
        }


    }
}
