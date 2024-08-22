package content.global.skill.production.cooking.recipe.pie.impl

import content.global.skill.production.cooking.recipe.pie.PieRecipe
import cfg.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Meat pie.
 */
class MeatPie : PieRecipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (removeItem(player, event.usedItem) && removeItem(player, event.baseItem)) {
            player.inventory.add(product)
            sendMessage(player, getMixMessage(event))
        }
    }

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(COOKED_MEAT, COOKED_CHICKEN, COOKED_RABBIT)
    }

    override fun getMixMessage(event: NodeUsageEvent): String {
        return "You fill the pie with meat."
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.UNCOOKED_MEAT_PIE_2319)
        private val COOKED_MEAT = Item(Items.COOKED_MEAT_2142)
        private val COOKED_CHICKEN = Item(Items.COOKED_CHICKEN_2140)
        private val COOKED_RABBIT = Item(Items.COOKED_RABBIT_3228)
    }
}
