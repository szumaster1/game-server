package content.global.skill.cooking.meat

import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents the skewered food.
 */
enum class SkeweredMeat(val raw: Item, val product: Item) {
    CHOMPY(
        raw = Item(Items.RAW_CHOMPY_2876),
        product = Item(Items.SKEWERED_CHOMPY_7230)
    ),
    RABBIT(
        raw = Item(Items.RAW_RABBIT_3226),
        product = Item(Items.SKEWERED_RABBIT_7224)
    ),
    BIRD(
        raw = Item(Items.RAW_BIRD_MEAT_9978),
        product = Item(Items.SKEWERED_BIRD_MEAT_9984)
    ),
    BEAST(
        raw = Item(Items.RAW_BEAST_MEAT_9986),
        product = Item(Items.SKEWERED_BEAST_9992)
    );

    companion object {
        @JvmStatic
        fun forItem(item: Item): SkeweredMeat? = values().find { it.raw.id == item.id }
    }
}
