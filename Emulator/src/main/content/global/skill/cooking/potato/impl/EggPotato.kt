package content.global.skill.cooking.potato.impl

import content.global.skill.cooking.potato.PotatoRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Egg potato.
 */
class EggPotato : PotatoRecipe() {

    override fun getProduct(): Item {
        return EGG_POTATO
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(TOPPING)
    }

    override fun isTopping(): Boolean {
        return true
    }

    override fun getLevel(): Int {
        return 51
    }

    override fun getExperience(): Double {
        return 10.0
    }

    companion object {
        private val EGG_POTATO = Item(Items.EGG_POTATO_7056)
        private val TOPPING = Item(Items.EGG_AND_TOMATO_7064)
    }
}
