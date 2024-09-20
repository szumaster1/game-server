package content.global.skill.cooking

import core.api.addItem
import org.rs.consts.Items
import core.api.getStatLevel
import core.api.removeItem
import core.api.sendDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Skewered food listener.
 */
class SkeweredFoodListener : InteractionListener {

    val skeweredFood = SkeweredSet.values().map { it.raw.id }.toIntArray()

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.IRON_SPIT_7225, *skeweredFood) { player, used, with ->
            if (getStatLevel(player, Skills.FIREMAKING) < 20) {
                sendDialogue(player, "You meed a Firemaking level of at least 20 in order to do this.")
                return@onUseWith false
            }
            val item = SkeweredSet.forItem(used.asItem())
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItem(player, item!!.product.id)
            }
            return@onUseWith true
        }
    }
}

/**
 * Represents the skewered food.
 */
enum class SkeweredSet(val raw: Item, val product: Item) {
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
        fun forItem(item: Item): SkeweredSet? = values().find { it.raw.id == item.id }
    }
}
