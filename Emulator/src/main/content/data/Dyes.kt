package content.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents a dye.
 * @author Vexia
 *
 * @param item the `Item(Item(Items.ID))` as example.
 */
enum class Dyes(val item: Item) {
    /**
     * Black.
     */
    BLACK(item = Item(Items.BLACK_MUSHROOM_INK_4622)),

    /**
     * Red.
     */
    RED(item = Item(Items.RED_DYE_1763)),

    /**
     * Yellow.
     */
    YELLOW(item = Item(Items.YELLOW_DYE_1765)),

    /**
     * Blue.
     */
    BLUE(item = Item(Items.BLUE_DYE_1767)),

    /**
     * Orange.
     */
    ORANGE(item = Item(Items.ORANGE_DYE_1769)),

    /**
     * Green.
     */
    GREEN(item = Item(Items.GREEN_DYE_1771)),

    /**
     * Purple.
     */
    PURPLE(item = Item(Items.PURPLE_DYE_1773)),

    /**
     * Pink.
     */
    PINK(item = Item(Items.PINK_DYE_6955));

    companion object {
        /**
         * Returns the Dyes enum for a given Item.
         *
         * @param item the Item to find the corresponding Dye for.
         * @return the Dyes enum associated with the provided Item, or null if not found.
         */
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
