package content.global.skill.production.herblore.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents a tar to create.
 *
 * @param ingredient    the main ingredient for the Tar.
 * @param level         the level required to use this Tar.
 * @param experience    the experience points gained from using this Tar.
 * @param tar           the resulting item after using the Tar.
 * @return the [Tar].
 */
enum class Tar(
    val ingredient: Item,   // The main ingredient for the Tar
    val level: Int,         // The level required to use this Tar
    val experience: Double, // Experience points gained from using this Tar
    val tar: Item           // The resulting item after using the Tar
) {
    /**
     * Guam Tar.
     */
    GUAM_TAR(
        ingredient = Herb.GUAM.product,
        level = 19,
        experience = 30.0,
        tar = Item(Items.GUAM_TAR_10142)
    ),

    /**
     * Marrentill Tar.
     */
    MARRENTILL_TAR(
        ingredient = Herb.MARRENTILL.product,
        level = 31,
        experience = 42.5,
        tar = Item(Items.MARRENTILL_TAR_10143)
    ),

    /**
     * Tarromin Tar.
     */
    TARROMIN_TAR(
        ingredient = Herb.TARROMIN.product,
        level = 39,
        experience = 55.0,
        tar = Item(Items.TARROMIN_TAR_10144)
    ),

    /**
     * Harralander Tar.
     */
    HARRALANDER_TAR(
        ingredient = Herb.HARRALANDER.product,
        level = 44,
        experience = 72.5,
        tar = Item(Items.HARRALANDER_TAR_10145)
    );

    companion object {

        /**
         * Gets the tar.
         *
         * @param item  the item.
         * @return the tar.
         */
        @JvmStatic
        fun forItem(item: Item): Tar? {
            for (tar in values()) {
                if (tar.ingredient.id == item.id) {
                    return tar
                }
            }
            return null
        }
    }
}
