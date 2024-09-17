package content.global.skill.production.cooking.recipe.pie.impl

import content.global.skill.production.cooking.recipe.pie.PieRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Redberry pie.
 */
class RedberryPie : PieRecipe() {

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(REDBERRIES)
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.UNCOOKED_BERRY_PIE_2321)
        private val REDBERRIES = Item(Items.REDBERRIES_1951)
    }
}
