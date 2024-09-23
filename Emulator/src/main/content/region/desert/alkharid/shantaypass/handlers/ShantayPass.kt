package content.region.desert.alkharid.shantaypass.handlers

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

/**
 * Represents the Shantay pass listeners.
 */
class ShantayPass : InteractionListener {

    companion object {
        private const val SHANTAY_NPC = NPCs.SHANTAY_836
        private const val SHANTAY_PASS_TICKET = Items.SHANTAY_PASS_1854
        private const val COINS = Items.COINS_995
    }

    override fun defineListeners() {

        /*
         * Listener for the interaction with the Shantay NPC when
         * the player chooses to buy a pass.
         */

        on(SHANTAY_NPC, IntType.NPC, "buy-pass") { player, node ->
            face(node.asNpc(), player, 2)
            if (removeItem(player, Item(COINS, 5))) {
                sendItemDialogue(player, SHANTAY_PASS_TICKET, "You purchase a Shantay Pass.")
                addItemOrDrop(player, SHANTAY_PASS_TICKET)
            } else {
                sendPlayerDialogue(player, "Sorry, I don't seem to have enough money.")
            }
            return@on true
        }
    }
}