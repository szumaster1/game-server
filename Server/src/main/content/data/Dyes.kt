package content.data

import core.api.consts.Items
import core.game.node.item.Item

enum class Dyes(val item: Item) {
    BLACK(Item(Items.BLACK_MUSHROOM_INK_4622)),
    RED(Item(Items.RED_DYE_1763)),
    YELLOW(Item(Items.YELLOW_DYE_1765)),
    BLUE(Item(Items.BLUE_DYE_1767)),
    ORANGE(Item(Items.ORANGE_DYE_1769)),
    GREEN(Item(Items.GREEN_DYE_1771)),
    PURPLE(Item(Items.PURPLE_DYE_1773)),
    PINK(Item(Items.PINK_DYE_6955));

    companion object {
        fun forItem(item: Item): Dyes? {
            for (d in values()) {
                if (d.item.id == item.id) {
                    return d
                }
            }
            return null
        }
    }
}
