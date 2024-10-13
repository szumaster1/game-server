package content.global.skill.cooking.type.pie.impl

import content.global.skill.cooking.type.pie.PieRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Mud pie.
 */
class MudPie : PieRecipe() {

    override fun getProduct(): Item {
        return UNCOOKED_PIE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(COMPOST, BUCKET_OF_WATER, CLAY)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(PIE_SHELL, PART_ONE, PART_TWO, UNCOOKED_PIE)
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val UNCOOKED_PIE = Item(Items.RAW_MUD_PIE_7168)
        private val COMPOST = Item(Items.COMPOST_6032)
        private val BUCKET_OF_WATER = Item(Items.BUCKET_OF_WATER_1929)
        private val CLAY = Item(Items.CLAY_434)
        private val PART_ONE = Item(Items.PART_MUD_PIE_7164)
        private val PART_TWO = Item(Items.PART_MUD_PIE_7166)
    }
}
