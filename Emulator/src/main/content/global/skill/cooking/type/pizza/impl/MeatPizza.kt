package content.global.skill.cooking.type.pizza.impl

import content.global.skill.cooking.type.pizza.PizzaRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Meat pizza.
 */
class MeatPizza : PizzaRecipe() {

    override fun getExperience(): Double {
        return 26.0
    }

    override fun getLevel(): Int {
        return 45
    }

    override fun getProduct(): Item {
        return MEAT_PIZZA
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(COOKED_MEAT, COOKED_CHICKEN)
    }

    companion object {
        private val MEAT_PIZZA = Item(Items.MEAT_PIZZA_2293)
        private val COOKED_MEAT = Item(Items.COOKED_MEAT_2142)
        private val COOKED_CHICKEN = Item(Items.COOKED_CHICKEN_2140)
    }
}
