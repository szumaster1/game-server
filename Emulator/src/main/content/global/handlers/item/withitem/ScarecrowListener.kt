package content.global.handlers.item.withitem

import core.api.*
import cfg.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills

/**
 * Handles use the watermelon on sack.
 */
class ScarecrowListener : InteractionListener {

    private val haySack = Items.HAY_SACK_6058
    private val watermelon = Items.WATERMELON_5982
    private val scarecrow = Items.SCARECROW_6059

    override fun defineListeners() {

        /*
         * Special interaction for creating Scarecrow.
         */

        onUseWith(IntType.ITEM, haySack, watermelon) { player, _, _ ->
            if (getStatLevel(player, Skills.FARMING) < 23) {
                removeItem(player, haySack)
                removeItem(player, watermelon)
                addItem(player, scarecrow)
                rewardXP(player, Skills.FARMING, 25.0)
                sendMessage(player, "You stab the watermelon on top of the spear, finishing your scarecrow.")
            } else {
                sendMessage(player, "Your Farming level is not high enough to do this.")
            }
            return@onUseWith true
        }
    }
}
