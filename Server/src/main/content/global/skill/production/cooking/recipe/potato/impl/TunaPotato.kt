package content.global.skill.production.cooking.recipe.potato.impl

import content.global.skill.production.cooking.recipe.potato.PotatoRecipe
import core.api.consts.Items
import core.game.node.item.Item

class TunaPotato : PotatoRecipe() {

    override fun getProduct(): Item {
        return TUNA_POTATO
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(TOPPING)
    }

    override fun isTopping(): Boolean {
        return true
    }

    override fun getLevel(): Int {
        return 68
    }

    override fun getExperience(): Double {
        return 10.0
    }

    companion object {
        private val TUNA_POTATO = Item(Items.TUNA_POTATO_7060)
        private val TOPPING = Item(Items.TUNA_AND_CORN_7068)
    }
}
