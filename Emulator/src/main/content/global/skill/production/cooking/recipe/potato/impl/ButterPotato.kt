package content.global.skill.production.cooking.recipe.potato.impl

import content.global.skill.production.cooking.recipe.Recipe
import org.rs.consts.Items
import core.api.getStatLevel
import core.api.rewardXP
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Butter potato.
 */
class ButterPotato : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (getStatLevel(player, Skills.COOKING) < 39) {
            sendDialogue(player, "You need a Cooking level of at least " + 39 + " in order to do this.")
            return
        }
        super.singleMix(player, event)
        rewardXP(player, Skills.COOKING, 40.5)
    }

    override fun getBase(): Item {
        return BAKED_POTATO
    }

    override fun getProduct(): Item {
        return POTATO_WITH_BUTTER
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(PAT_OF_BUTTER)
    }

    override fun getParts(): Array<Item> {
        return arrayOf()
    }

    override fun getMixMessage(event: NodeUsageEvent): String {
        return "You add a pat of butter to the potato."
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val BAKED_POTATO = Item(Items.BAKED_POTATO_6701)
        private val POTATO_WITH_BUTTER = Item(Items.POTATO_WITH_BUTTER_6703)
        private val PAT_OF_BUTTER = Item(Items.PAT_OF_BUTTER_6697)
    }
}
