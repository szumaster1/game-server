package content.global.skill.production.herblore.data

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Tar
 *
 * @property ingredient
 * @property level
 * @property experience
 * @property tar
 * @constructor Tar
 */
enum class Tar(
    val ingredient: Item,
    val level: Int,
    val experience: Double,
    val tar: Item
) {
    /**
     * Guam Tar
     *
     * @constructor Guam Tar
     */
    GUAM_TAR(
        ingredient = Herb.GUAM.product,
        level = 19,
        experience = 30.0,
        tar = Item(Items.GUAM_TAR_10142)
    ),

    /**
     * Marrentill Tar
     *
     * @constructor Marrentill Tar
     */
    MARRENTILL_TAR(
        ingredient = Herb.MARRENTILL.product,
        level = 31,
        experience = 42.5,
        tar = Item(Items.MARRENTILL_TAR_10143)
    ),

    /**
     * Tarromin Tar
     *
     * @constructor Tarromin Tar
     */
    TARROMIN_TAR(
        ingredient = Herb.TARROMIN.product,
        level = 39,
        experience = 55.0,
        tar = Item(Items.TARROMIN_TAR_10144)
    ),

    /**
     * Harralander Tar
     *
     * @constructor Harralander Tar
     */
    HARRALANDER_TAR(
        ingredient = Herb.HARRALANDER.product,
        level = 44,
        experience = 72.5,
        tar = Item(Items.HARRALANDER_TAR_10145)
    );

    companion object {
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
