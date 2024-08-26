package content.global.skill.production.crafting.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Pottery data
 *
 * @param unfinished Represents the unfinished state of the pottery item.
 * @param product Represents the final product of the pottery item.
 * @param level Indicates the level of the pottery item.
 * @param exp Represents the experience points associated with the pottery item.
 * @param fireExp Represents the experience points gained from firing the pottery item.
 * @constructor Pottery data Represents the properties of the Pottery enum.
 */
enum class Pottery(
    val unfinished: Item,
    val product: Item,
    val level: Int,
    val exp: Double,
    val fireExp: Double
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
        fun forId(id: Int): Pottery? {
            for (def in values()) {
                if (def.unfinished.id == id) {
                    return def
                }
            }
            return null
        }
    }
}
