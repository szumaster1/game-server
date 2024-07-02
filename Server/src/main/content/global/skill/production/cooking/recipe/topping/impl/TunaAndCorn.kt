package content.global.skill.production.cooking.recipe.topping.impl

import content.global.skill.production.cooking.recipe.Recipe
import core.api.consts.Items
import core.api.getStatLevel
import core.api.rewardXP
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class TunaAndCorn : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (getStatLevel(player, Skills.COOKING) < 67) {
            sendDialogue(player, "You need a Cooking level of at least 57 in order to do this.")
            return
        }
        super.mix(player, event)
        rewardXP(player, Skills.COOKING, 204.0)
    }

    override fun getBase(): Item {
        return CHOPPED_TUNA
    }

    override fun getProduct(): Item {
        return TUNA_AND_CORN
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(COOKED_CORN)
    }

    override fun getParts(): Array<Item> {
        return emptyArray()
    }

    override fun getMixMessage(event: NodeUsageEvent): String? {
        return null
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val CHOPPED_TUNA = Item(Items.CHOPPED_TUNA_7086)
        private val COOKED_CORN = Item(Items.COOKED_SWEETCORN_5988)
        private val TUNA_AND_CORN = Item(Items.TUNA_AND_CORN_7068)
    }
}

