package content.global.skill.cooking.type.dairy

import org.rs.consts.Items
import core.game.node.item.Item
import java.util.*

/**
 * Represents the dairy product.
 */
enum class DairyProduct(
    val level: Int,
    val experience: Double,
    val product: Item,
    inputs: Array<Int>
) {
    POT_OF_CREAM(
        level = 21,
        experience = 18.0,
        product = Item(Items.POT_OF_CREAM_2130, 1),
        inputs = arrayOf(Items.BUCKET_OF_MILK_1927)
    ),
    PAT_OF_BUTTER(
        level = 38,
        experience = 40.5,
        product = Item(Items.PAT_OF_BUTTER_6697, 1),
        inputs = arrayOf(Items.BUCKET_OF_MILK_1927, Items.POT_OF_CREAM_2130)
    ),
    CHEESE(
        level = 48,
        experience = 64.0,
        product = Item(Items.CHEESE_1985, 1),
        inputs = arrayOf(Items.BUCKET_OF_MILK_1927, Items.POT_OF_CREAM_2130, Items.PAT_OF_BUTTER_6697)
    );

    val inputs: Array<Item?> =
        Arrays.stream(inputs).map { id: Int? -> Item(id!!, 1) }.toArray { len: Int -> arrayOfNulls(len) }
}
