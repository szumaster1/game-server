package content.data.item

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the repair item type.
 *
 * @param item The broken item to be repaired.
 * @param product The repaired item.
 * @param cost The cost of repairing the item.
 * @return the [RepairItem].
 */
enum class RepairItem(val item: Item, val product: Item, val cost: Int) {
    BRONZE_HATCHET(
        item = Item(Items.BROKEN_AXE_494, 1),
        product = Item(Items.BRONZE_AXE_1351, 1),
        cost = 0
    ),
    IRON_HATCHET(
        item = Item(Items.BROKEN_AXE_496, 1),
        product = Item(Items.IRON_AXE_1349, 1),
        cost = 0
    ),
    STEEL_HATCHET(
        item = Item(Items.BROKEN_AXE_498, 1),
        product = Item(Items.STEEL_AXE_1353, 1),
        cost = 0
    ),
    BLACK_HATCHET(
        item = Item(Items.BROKEN_AXE_500, 1),
        product = Item(Items.BLACK_AXE_1361, 1),
        cost = 10
    ),
    MITHRIL_HATCHET(
        item = Item(Items.BROKEN_AXE_502, 1),
        product = Item(Items.MITHRIL_AXE_1355, 1),
        cost = 18
    ),
    ADAMANT_HATCHET(
        item = Item(Items.BROKEN_AXE_504, 1),
        product = Item(Items.ADAMANT_AXE_1357, 1),
        cost = 43
    ),
    RUNE_HATCHET(
        item = Item(Items.BROKEN_AXE_506, 1),
        product = Item(Items.RUNE_AXE_1359, 1),
        cost = 427
    ),
    DRAGON_HATCHET(
        item = Item(Items.BROKEN_AXE_6741, 1),
        product = Item(Items.DRAGON_AXE_6739, 1),
        cost = 1800
    ),

    BRONZE_PICKAXE(
        item = Item(Items.BROKEN_PICKAXE_468, 1),
        product = Item(Items.BRONZE_PICKAXE_1265, 1),
        cost = 0
    ),
    IRON_PICKAXE(
        item = Item(Items.BROKEN_PICKAXE_470, 1),
        product = Item(Items.IRON_PICKAXE_1267, 1),
        cost = 0
    ),
    STEEL_PICKAXE(
        item = Item(Items.BROKEN_PICKAXE_472, 1),
        product = Item(Items.STEEL_PICKAXE_1269, 1),
        cost = 14
    ),
    MITHRIL_PICKAXE(
        item = Item(Items.BROKEN_PICKAXE_474, 1),
        product = Item(Items.MITHRIL_PICKAXE_1273, 1),
        cost = 43
    ),
    ADAMANT_PICKAXE(
        item = Item(Items.BROKEN_PICKAXE_476, 1),
        product = Item(Items.ADAMANT_PICKAXE_1271, 1),
        cost = 107
    ),
    RUNE_PICKAXE(
        item = Item(Items.BROKEN_PICKAXE_478, 1),
        product = Item(Items.RUNE_PICKAXE_1275, 1),
        cost = 1100
    );

    companion object {
        @JvmStatic
        fun forId(id: Int): RepairItem? {
            for (item in values()) if (item.item.id == id) return item
            return null
        }
    }
}
