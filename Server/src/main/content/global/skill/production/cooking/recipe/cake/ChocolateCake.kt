package content.global.skill.production.cooking.recipe.cake

import content.global.skill.production.cooking.recipe.Recipe
import core.api.consts.Items
import core.api.getStatLevel
import core.api.rewardXP
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class ChocolateCake : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (getStatLevel(player, Skills.COOKING) < 50) {
            sendDialogue(player, "You need a Cooking level of 50 in order to do that.")
            return
        }
        super.mix(player, event)
        rewardXP(player, Skills.COOKING, 30.0)
    }

    override fun getBase(): Item {
        return CAKE
    }

    override fun getProduct(): Item {
        return CHOCOLATE_CAKE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(CHOCOLATE_BAR)
    }

    override fun getParts(): Array<Item> {
        return arrayOf()
    }

    override fun getMixMessage(event: NodeUsageEvent): String {
        return "You add chocolate to the cake."
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val CAKE = Item(Items.CAKE_1891)
        private val CHOCOLATE_CAKE = Item(Items.CHOCOLATE_CAKE_1897)
        private val CHOCOLATE_BAR = Item(Items.CHOCOLATE_BAR_1973)
    }
}
