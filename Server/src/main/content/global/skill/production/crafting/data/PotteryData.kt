package content.global.skill.production.crafting.data

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Pottery data
 *
 * @property unfinished Represents the unfinished state of the pottery item.
 * @property product Represents the final product of the pottery item.
 * @property level Indicates the level of the pottery item.
 * @property exp Represents the experience points associated with the pottery item.
 * @property fireExp Represents the experience points gained from firing the pottery item.
 * @constructor Pottery data Initializes the properties of the PotteryData enum.
 */
enum class PotteryData(
    val unfinished: Item, // Holds the unfinished item representation
    val product: Item,    // Holds the final product representation
    val level: Int,       // Indicates the level of the pottery item
    val exp: Double,      // Experience points for the pottery item
    val fireExp: Double    // Experience points gained from firing the pottery item
) {
    /**
     * Pot.
     */
    POT(
        unfinished = Item(Items.UNFIRED_POT_1787),
        product = Item(Items.EMPTY_POT_1931),
        level = 1,
        exp = 6.3,
        fireExp = 6.3
    ),

    /**
     * Dish.
     */
    DISH(
        unfinished = Item(Items.UNFIRED_PIE_DISH_1789),
        product = Item(Items.PIE_DISH_2313),
        level = 7,
        exp = 15.0,
        fireExp = 10.0
    ),

    /**
     * Bowl.
     */
    BOWL(unfinished = Item(Items.UNFIRED_BOWL_1791),
        product = Item(Items.BOWL_1923),
        level = 8, 18.0,
        fireExp = 15.0
    ),

    /**
     * Plant.
     */
    PLANT(
        unfinished = Item(Items.UNFIRED_PLANT_POT_5352),
        product = Item(Items.PLANT_POT_5350),
        level = 19,
        exp = 20.0,
        fireExp = 17.5
    ),

    /**
     * Lid.
     */
    LID(
        unfinished = Item(Items.UNFIRED_POT_LID_4438),
        product = Item(Items.POT_LID_4440),
        level = 25,
        exp = 20.0,
        fireExp = 20.0
    );

    companion object {
        @JvmStatic
        fun forId(id: Int): PotteryData? {
            for (def in values()) {
                if (def.unfinished.id == id) {
                    return def
                }
            }
            return null
        }
    }
}
