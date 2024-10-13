package content.global.skill.cooking.type.potato.impl

import content.global.skill.cooking.type.potato.PotatoRecipe
import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Mushroom potato.
 */
class MushroomPotato : PotatoRecipe() {

    override fun getProduct(): Item {
        return MUSHROOM_POTATO
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(TOPPING)
    }

    override fun isTopping(): Boolean {
        return true
    }

    override fun getLevel(): Int {
        return 64
    }

    override fun getExperience(): Double {
        return 10.0
    }

    companion object {
        private val MUSHROOM_POTATO = Item(Items.MUSHROOM_POTATO_7058)
        private val TOPPING = Item(Items.MUSHROOM_AND_ONION_7066)
    }
}
