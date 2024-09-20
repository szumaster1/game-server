package content.global.skill.cooking.recipe.pie.impl

import content.global.skill.cooking.recipe.pie.PieRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Garden pie.
 */
class GardenPie : PieRecipe() {

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(TOMATO, ONION, CABBAGE)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(PIE_SHELL, PART_ONE, PART_TWO, UNCOOKED_PIE)
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.RAW_GARDEN_PIE_7176)
        private val TOMATO = Item(Items.TOMATO_1982)
        private val ONION = Item(Items.ONION_1957)
        private val CABBAGE = Item(Items.CABBAGE_1965)
        private val PART_ONE = Item(Items.PART_GARDEN_PIE_7172)
        private val PART_TWO = Item(Items.PART_GARDEN_PIE_7174)
    }
}
