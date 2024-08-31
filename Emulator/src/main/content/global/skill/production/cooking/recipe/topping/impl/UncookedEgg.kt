package content.global.skill.production.cooking.recipe.topping.impl

import content.global.skill.production.cooking.recipe.topping.ToppingRecipe
import cfg.consts.Items
import core.game.node.item.Item

/**
 * Uncooked egg.
 */
class UncookedEgg : ToppingRecipe() {

    override fun getLevel(): Int {
        return 1
    }

    override fun getExperience(): Double {
        return 1.0
    }

    override fun getProduct(): Item {
        return UNCOOKED_EGG
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(EGG)
    }

    override fun isSingular(): Boolean {
        return true
    }

    override fun getParts(): Array<Item> {
        return emptyArray()
    }

    companion object {
        private val EGG = Item(Items.EGG_1944)
        private val UNCOOKED_EGG = Item(Items.UNCOOKED_EGG_7076)
    }
}

