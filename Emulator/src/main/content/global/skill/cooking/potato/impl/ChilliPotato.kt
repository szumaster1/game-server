package content.global.skill.cooking.potato.impl

import content.global.skill.cooking.potato.PotatoRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Chilli potato.
 */
class ChilliPotato : PotatoRecipe() {

    override fun getProduct(): Item {
        return CHILLI_POTATO
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(CHILLI_CON_CARNE)
    }

    override fun isTopping(): Boolean {
        return true
    }

    override fun getLevel(): Int {
        return 41
    }

    override fun getExperience(): Double {
        return 10.0
    }

    companion object {
        private val CHILLI_POTATO = Item(Items.CHILLI_POTATO_7054)
        private val CHILLI_CON_CARNE = Item(Items.CHILLI_CON_CARNE_7062)
    }
}
