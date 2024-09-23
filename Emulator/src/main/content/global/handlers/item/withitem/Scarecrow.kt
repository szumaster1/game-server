package content.global.handlers.item.withitem

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import org.rs.consts.Items

/**
 * Creating scarecrow.
 */
class Scarecrow : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.HAY_SACK_6058, Items.WATERMELON_5982) { player, _, _ ->
            if (getStatLevel(player, Skills.FARMING) < 23) {
                removeItem(player, Items.HAY_SACK_6058)
                removeItem(player, Items.WATERMELON_5982)
                addItem(player, Items.SCARECROW_6059)
                rewardXP(player, Skills.FARMING, 25.0)
                sendMessage(player, "You stab the watermelon on top of the spear, finishing your scarecrow.")
            } else {
                sendMessage(player, "Your Farming level is not high enough to do this.")
            }
            return@onUseWith true
        }
    }
}
