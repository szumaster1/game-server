package content.global.skill.production.herblore.data

import core.api.consts.Items
import core.game.node.item.Item

enum class Tar(
    val ingredient: Item,
    val level: Int,
    val experience: Double,
    val tar: Item
) {
    GUAM_TAR(
        ingredient = Herb.GUAM.product,
        level = 19,
        experience = 30.0,
        tar = Item(Items.GUAM_TAR_10142)
    ),
    MARRENTILL_TAR(
        ingredient = Herb.MARRENTILL.product,
        level = 31,
        experience = 42.5,
        tar = Item(Items.MARRENTILL_TAR_10143)
    ),
    TARROMIN_TAR(
        ingredient = Herb.TARROMIN.product,
        level = 39,
        experience = 55.0,
        tar = Item(Items.TARROMIN_TAR_10144)
    ),
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
