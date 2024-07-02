package content.global.skill.production.cooking.recipe.topping.impl

import content.global.skill.production.cooking.recipe.topping.ToppingRecipe
import core.api.consts.Items
import core.api.inInventory
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.item.Item

class SlicedMushroom : ToppingRecipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (!inInventory(player, Items.KNIFE_946)) {
            sendDialogue(player, "You need a knife in order to slice up the mushrooms.")
            return
        }
        super.mix(player, event)
    }

    override fun getLevel(): Int {
        return 1
    }

    override fun getExperience(): Double {
        return 0.0
    }

    override fun getProduct(): Item {
        return SLICED_MUSHROOMS
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(MUSHROOM)
    }

    override fun getParts(): Array<Item> {
        return emptyArray()
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val SLICED_MUSHROOMS = Item(Items.SLICED_MUSHROOMS_7080)
        private val MUSHROOM = Item(Items.MUSHROOM_6004)
    }
}

