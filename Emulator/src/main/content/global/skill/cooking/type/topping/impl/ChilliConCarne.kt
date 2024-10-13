package content.global.skill.cooking.type.topping.impl

import content.global.skill.cooking.type.Recipe
import org.rs.consts.Items
import core.api.getStatLevel
import core.api.inInventory
import core.api.rewardXP
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Chilli con carne.
 */
class ChilliConCarne : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (getStatLevel(player, Skills.COOKING) < 9) {
            sendDialogue(player, "You need a Cooking level of at least " + 9 + " in order to do this.")
            return
        }
        if (!inInventory(player, Items.KNIFE_946)) {
            sendDialogue(player, "You need a knife in order to cut up the meat.")
            return
        }
        super.mix(player, event)
        rewardXP(player, Skills.COOKING, 25.0)
    }

    override fun getBase(): Item {
        return SPICY_SAUCE
    }

    override fun getProduct(): Item {
        return CHILLI_CON_CARNE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(COOKED_MEAT)
    }

    override fun getParts(): Array<Item> {
        return arrayOf()
    }

    override fun getMixMessage(event: NodeUsageEvent): String {
        return "You put the cut up meat into the bowl."
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val SPICY_SAUCE = Item(Items.SPICY_SAUCE_7072)
        private val CHILLI_CON_CARNE = Item(Items.CHILLI_CON_CARNE_7062)
        private val COOKED_MEAT = Item(Items.COOKED_MEAT_2142)
    }
}
