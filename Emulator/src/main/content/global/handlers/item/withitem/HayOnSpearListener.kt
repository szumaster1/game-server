package content.global.handlers.item.withitem

import core.api.addItem
import cfg.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Handles use hay on a spear.
 */
class HayOnSpearListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Bronze spear used on the hay sack to create a hay sack spear.
         */

        onUseWith(IntType.ITEM, Items.HAY_SACK_6057, Items.BRONZE_SPEAR_1237) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                sendMessage(player, "You stab the hay sack with a spear.")
                addItem(player, Items.HAY_SACK_6058, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }
    }
}
