package content.global.skill.production.cooking.recipe.pie.impl

import content.global.skill.production.cooking.recipe.pie.PieRecipe
import core.api.consts.Items
import core.game.node.item.Item

class WildPie : PieRecipe() {

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(BEAR_MEAT, CHOMPY_MEAT, RABBIT_MEAT)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(PIE_SHELL, PART_ONE, PART_TWO, UNCOOKED_PIE)
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.RAW_WILD_PIE_7206)
        private val BEAR_MEAT = Item(Items.RAW_BEAR_MEAT_2136)
        private val CHOMPY_MEAT = Item(Items.RAW_CHOMPY_2876)
        private val RABBIT_MEAT = Item(Items.RAW_RABBIT_3226)
        private val PART_ONE = Item(Items.PART_WILD_PIE_7202)
        private val PART_TWO = Item(Items.PART_WILD_PIE_7204)
    }
}
