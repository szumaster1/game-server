package content.global.skill.cooking.type.pie.impl

import content.global.skill.cooking.type.pie.PieRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Summer pie.
 */
class SummerPie : PieRecipe() {

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(STRAWBERRY, WATERMELON, COOKING_APPLE)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(PIE_SHELL, PART_ONE, PART_TWO, UNCOOKED_PIE)
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.RAW_SUMMER_PIE_7216)
        private val STRAWBERRY = Item(Items.STRAWBERRY_5504)
        private val WATERMELON = Item(Items.WATERMELON_5982)
        private val COOKING_APPLE = Item(Items.COOKING_APPLE_1955)
        private val PART_ONE = Item(Items.PART_SUMMER_PIE_7212)
        private val PART_TWO = Item(Items.PART_SUMMER_PIE_7214)
    }
}
