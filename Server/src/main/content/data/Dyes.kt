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
    BLACK(Item(Items.BLACK_MUSHROOM_INK_4622)),

    /**
     * Red.
     */
    RED(Item(Items.RED_DYE_1763)),

    /**
     * Yellow.
     */
    YELLOW(Item(Items.YELLOW_DYE_1765)),

    /**
     * Blue.
     */
    BLUE(Item(Items.BLUE_DYE_1767)),

    /**
     * Orange.
     */
    ORANGE(Item(Items.ORANGE_DYE_1769)),

    /**
     * Green.
     */
    GREEN(Item(Items.GREEN_DYE_1771)),

    /**
     * Purple.
     */
    PURPLE(Item(Items.PURPLE_DYE_1773)),

    /**
     * Pink.
     */
    PINK(Item(Items.PINK_DYE_6955));

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
