package content.global.skill.production.cooking.dairy

import core.api.consts.Items
import core.game.node.item.Item
import java.util.*

/**
 * Dairy product
 *
 * @param level The level of the dairy product, represented as an integer.
 * @param experience The experience points associated with the dairy product, represented as a double.
 * @param product The item representing the dairy product.
 * @param inputs An array of integers representing additional inputs related to the dairy product.
 */
enum class DairyProduct(
    val level: Int, // The level of the dairy product
    val experience: Double, // The experience points gained from the dairy product
    val product: Item, // The item that corresponds to the dairy product
    inputs: Array<Int> // Additional inputs related to the dairy product
) {
    /**
     * Pot Of Cream.
     */
    POT_OF_CREAM(
        level = 21,
        experience = 18.0,
        product = Item(Items.POT_OF_CREAM_2130, 1),
        inputs = arrayOf(Items.BUCKET_OF_MILK_1927)
    ),

    /**
     * Pat Of Butter.
     */
    PAT_OF_BUTTER(
        level = 38,
        experience = 40.5,
        product = Item(Items.PAT_OF_BUTTER_6697, 1),
        inputs = arrayOf(Items.BUCKET_OF_MILK_1927, Items.POT_OF_CREAM_2130)
    ),

    /**
     * Cheese.
     */
    CHEESE(
        level = 48,
        experience = 64.0,
        product = Item(Items.CHEESE_1985, 1),
        inputs = arrayOf(Items.BUCKET_OF_MILK_1927, Items.POT_OF_CREAM_2130, Items.PAT_OF_BUTTER_6697)
    );

    val inputs: Array<Item?> =
        Arrays.stream(inputs).map { id: Int? -> Item(id!!, 1) }.toArray { len: Int -> arrayOfNulls(len) }
}
