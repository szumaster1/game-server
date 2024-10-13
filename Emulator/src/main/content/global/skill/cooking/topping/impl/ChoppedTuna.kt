package content.global.skill.cooking.topping.impl

import content.global.skill.cooking.topping.ToppingRecipe
import org.rs.consts.Items
import core.api.inInventory
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Chopped tuna.
 */
class ChoppedTuna : ToppingRecipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (!inInventory(player, Items.KNIFE_946)) {
            sendDialogue(player, "You need a knife in order to slice up the tuna.")
            return
        }
        super.mix(player, event)
    }

    override fun getLevel(): Int {
        return 1
    }

    override fun getExperience(): Double {
        return 1.0
    }

    override fun getProduct(): Item {
        return CHOPPED_TUNA
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(TUNA)
    }

    override fun getParts(): Array<Item> {
        return arrayOf()
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val CHOPPED_TUNA = Item(Items.CHOPPED_TUNA_7086)
        private val TUNA = Item(Items.TUNA_361)
    }
}
