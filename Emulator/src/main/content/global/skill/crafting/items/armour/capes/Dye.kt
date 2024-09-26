package content.global.skill.crafting.items.armour.capes

import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents a dye.
 */
enum class Dye(val item: Item) {
    BLACK(item = Item(Items.BLACK_MUSHROOM_INK_4622)),
    RED(item = Item(Items.RED_DYE_1763)),
    YELLOW(item = Item(Items.YELLOW_DYE_1765)),
    BLUE(item = Item(Items.BLUE_DYE_1767)),
    ORANGE(item = Item(Items.ORANGE_DYE_1769)),
    GREEN(item = Item(Items.GREEN_DYE_1771)),
    PURPLE(item = Item(Items.PURPLE_DYE_1773)),
    PINK(item = Item(Items.PINK_DYE_6955));

    companion object {
        /**
         * Get a dye for given item.
         *
         * @param item the item.
         * @return the dye.
         */
        @JvmStatic
        fun forItem(item: Item): Dye? {
            for (d in values()) {
                if (d.item.id == item.id) {
                    return d
                }
            }
            return null
        }
    }
}