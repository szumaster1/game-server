package content.global.skill.herblore.herbs

import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Represents a tar to create.
 */
enum class Tar(
    val ingredient: Int,
    val level: Int,
    val experience: Double,
    val product: Int
) {
    GUAM_TAR(
        ingredient = Herb.GUAM.product.id,
        level = 19,
        experience = 30.0,
        product = Items.GUAM_TAR_10142
    ),
    GROUND_GUAM_TAR(
        ingredient = Items.GROUND_GUAM_6681,
        level = 19,
        experience = 30.0,
        product = Items.GUAM_TAR_10142
    ),
    MARRENTILL_TAR(
        ingredient = Herb.MARRENTILL.product.id,
        level = 31,
        experience = 42.5,
        product = Items.MARRENTILL_TAR_10143
    ),
    TARROMIN_TAR(
        ingredient = Herb.TARROMIN.product.id,
        level = 39,
        experience = 55.0,
        product = Items.TARROMIN_TAR_10144
    ),
    HARRALANDER_TAR(
        ingredient = Herb.HARRALANDER.product.id,
        level = 44,
        experience = 72.5,
        product = Items.HARRALANDER_TAR_10145
    );

    companion object {
        /**
         * Gets the tar.
         * @param item the item.
         * @return the tar.
         */
        @JvmStatic
        fun forItem(item: Item): Tar? {
            for (tar in values()) {
                if (tar.ingredient == item.id) {
                    return tar
                }
            }
            return null
        }
    }
}
