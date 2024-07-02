package content.global.skill.production.cooking.recipe.pizza.impl

import content.global.skill.production.cooking.recipe.pizza.PizzaRecipe
import core.api.consts.Items
import core.game.interaction.NodeUsageEvent
import core.game.node.item.Item

class PineapplePizza : PizzaRecipe() {

    override fun getExperience(): Double {
        return 52.0
    }

    override fun getLevel(): Int {
        return 65
    }

    override fun getProduct(): Item {
        return PINEAPPLE_PIZZA
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(PINEAPPLE_CHUNKS, PINEAPPLE_RING)
    }

    override fun getMixMessage(event: NodeUsageEvent): String {
        return "You add the " + event.baseItem.name.lowercase() + " to the pizza."
    }

    companion object {
        private val PINEAPPLE_PIZZA = Item(Items.PINEAPPLE_PIZZA_2301)
        private val PINEAPPLE_RING = Item(Items.PINEAPPLE_RING_2118)
        private val PINEAPPLE_CHUNKS = Item(Items.PINEAPPLE_CHUNKS_2116)
    }
}
