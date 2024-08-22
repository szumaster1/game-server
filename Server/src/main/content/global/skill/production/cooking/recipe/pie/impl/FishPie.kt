package content.global.skill.production.cooking.recipe.pie.impl

import content.global.skill.production.cooking.recipe.pie.PieRecipe
import cfg.consts.Items
import core.game.node.item.Item

/**
 * Fish pie.
 */
class FishPie : PieRecipe() {

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(TROUT, COD, POTATO)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(PIE_SHELL, PART_ONE, PART_TWO, UNCOOKED_PIE)
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.RAW_FISH_PIE_7186)
        private val TROUT = Item(Items.TROUT_333)
        private val COD = Item(Items.COD_339)
        private val POTATO = Item(Items.POTATO_1942)
        private val PART_ONE = Item(Items.PART_FISH_PIE_7182)
        private val PART_TWO = Item(Items.PART_FISH_PIE_7184)
    }
}
