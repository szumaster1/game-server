package content.global.skill.production.cooking.handlers

import content.global.skill.production.cooking.fermenting.WineFermentingPulse
import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class SkeweredFoodListener : InteractionListener {

    val skeweredFood = SkeweredSet.values().map { it.raw.id }.toIntArray()

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.IRON_SPIT_7225, *skeweredFood) { player, used, with ->
            if (player.getSkills().getLevel(Skills.FIREMAKING) < 20) {
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

enum class SkeweredSet(val raw: Item, val product: Item) {
    CHOMPY(raw = Item(Items.RAW_CHOMPY_2876), product = Item(Items.SKEWERED_CHOMPY_7230)),
    RABBIT(raw = Item(Items.RAW_RABBIT_3226), product = Item(Items.SKEWERED_RABBIT_7224)),
    BIRD(raw = Item(Items.RAW_BIRD_MEAT_9978), product = Item(Items.SKEWERED_BIRD_MEAT_9984)),
    BEAST(raw = Item(Items.RAW_BEAST_MEAT_9986), product = Item(Items.SKEWERED_BEAST_9992));


    companion object {

        fun forItem(item: Item): SkeweredSet? {
            for (set in values()) {
                if (set.raw.id == item.id) {
                    return set
                }
            }
            return null
        }
    }
}
