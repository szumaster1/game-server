package content.global.skill.production.cooking.recipe.topping.impl

import content.global.skill.production.cooking.recipe.topping.ToppingRecipe
import core.api.consts.Items
import core.api.inInventory
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.item.Item

class ChoppedOnion : ToppingRecipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (!inInventory(player, Items.KNIFE_946)) {
            sendDialogue(player, "You need a knife in order to slice up the onion.")
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
        return CHOPPED_ONION
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(ONION)
    }

    override fun getParts(): Array<Item> {
        return arrayOf()
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val CHOPPED_ONION = Item(Items.CHOPPED_ONION_1871)
        private val ONION = Item(Items.ONION_1957)
    }
}
