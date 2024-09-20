package content.global.skill.cooking.recipe.topping.impl

import content.global.skill.cooking.recipe.Recipe
import org.rs.consts.Items
import core.api.getStatLevel
import core.api.rewardXP
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Egg and tomato.
 */
class EggAndTomato : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (getStatLevel(player, Skills.COOKING) < 23) {
            sendDialogue(player, "You need a Cooking level of at least " + 23 + " in order to do this.")
            return
        }
        super.mix(player, event)
        rewardXP(player, Skills.COOKING, 50.0)
    }

    override fun getBase(): Item {
        return SCRAMBLED_EGG
    }

    override fun getProduct(): Item {
        return EGG_AND_TOMATO
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(TOMATO)
    }

    override fun getParts(): Array<Item> {
        return arrayOf()
    }

    override fun getMixMessage(event: NodeUsageEvent): String? {
        return null
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val EGG_AND_TOMATO = Item(Items.EGG_AND_TOMATO_7064)
        private val SCRAMBLED_EGG = Item(Items.SCRAMBLED_EGG_7078)
        private val TOMATO = Item(Items.TOMATO_1982)
    }
}
