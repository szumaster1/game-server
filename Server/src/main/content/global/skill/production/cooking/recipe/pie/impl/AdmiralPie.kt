package content.global.skill.production.cooking.recipe.pie.impl

import content.global.skill.production.cooking.recipe.pie.PieRecipe
import core.api.consts.Items
import core.game.node.item.Item

/**
 * Admiral pie.
 */
class AdmiralPie : PieRecipe() {

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(SALMON, TUNA, POTATO)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(PIE_SHELL, PART_ONE, PART_TWO, UNCOOKED_PIE)
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.RAW_ADMIRAL_PIE_7196)
        private val SALMON = Item(Items.SALMON_329)
        private val TUNA = Item(Items.TUNA_361)
        private val POTATO = Item(Items.POTATO_1942)
        private val PART_ONE = Item(Items.PART_ADMIRAL_PIE_7192)
        private val PART_TWO = Item(Items.PART_ADMIRAL_PIE_7194)
    }
}
