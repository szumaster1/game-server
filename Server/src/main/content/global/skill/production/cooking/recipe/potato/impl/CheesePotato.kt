package content.global.skill.production.cooking.recipe.potato.impl

import content.global.skill.production.cooking.recipe.potato.PotatoRecipe
import core.api.consts.Items
import core.game.node.item.Item

/**
 * Cheese potato.
 */
class CheesePotato : PotatoRecipe() {

    override fun getProduct(): Item {
        return CHEESE_POTATO
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(CHEESE)
    }

    override fun isTopping(): Boolean {
        return false
    }

    override fun getLevel(): Int {
        return 47
    }

    override fun getExperience(): Double {
        return 10.0
    }

    companion object {
        private val CHEESE_POTATO = Item(Items.POTATO_WITH_CHEESE_6705)
        private val CHEESE = Item(Items.CHEESE_1985)
    }
}
