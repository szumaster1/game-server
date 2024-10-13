package content.global.skill.cooking.type.pizza.impl

import content.global.skill.cooking.type.pizza.PizzaRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Anchovy pizza.
 */
class AnchovyPizza : PizzaRecipe() {

    override fun getExperience(): Double {
        return 39.0
    }

    override fun getProduct(): Item {
        return ANCHOVY_PIZZA
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(ANCHOVIES)
    }

    override fun getLevel(): Int {
        return 55
    }

    companion object {
        private val ANCHOVY_PIZZA = Item(Items.ANCHOVY_PIZZA_2297)
        private val ANCHOVIES = Item(Items.ANCHOVIES_319)
    }
}
