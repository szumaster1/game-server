package content.global.skill.crafting.items.armour.capes

import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents different types of capes with associated dye and product.
 *
 * @param [dye] the dye color.
 * @param [product] the product.
 */
enum class Cape(val dye: Dye, val cape: Item) {
    BLACK(
        dye = Dye.BLACK,
        cape = Item(Items.BLACK_CAPE_1019)
    ),
    RED(
        dye = Dye.RED,
        cape = Item(Items.RED_CAPE_1007)
    ),
    BLUE(
        dye = Dye.BLUE,
        cape = Item(Items.BLUE_CAPE_1021)
    ),
    YELLOW(
        dye = Dye.YELLOW,
        cape = Item(Items.YELLOW_CAPE_1023)
    ),
    GREEN(
        dye = Dye.GREEN,
        cape = Item(Items.GREEN_CAPE_1027)
    ),
    PURPLE(
        dye = Dye.PURPLE,
        cape = Item(Items.PURPLE_CAPE_1029)
    ),
    ORANGE(
        dye = Dye.ORANGE,
        cape = Item(Items.ORANGE_CAPE_1031)
    ),
    PINK(
        dye = Dye.PINK,
        cape = Item(Items.PINK_CAPE_6959)
    );

    companion object {
        @JvmStatic
        fun forDye(dye: Int): Cape? {
            for (cape in Cape.values()) {
                if (cape.dye.item.id == dye) {
                    return cape
                }
            }
            return null
        }
    }
}