package content.global.skill.production.cooking.recipe.pie.impl

import content.global.skill.production.cooking.recipe.pie.PieRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Apple pie.
 */
class ApplePie : PieRecipe() {

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(COOKING_APPLE)
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.UNCOOKED_APPLE_PIE_2317)
        private val COOKING_APPLE = Item(Items.COOKING_APPLE_1955)
    }
}
