package content.region.desert.handlers

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

/**
 * Represents the Shantay pass listeners.
 */
class ShantayPassListeners : InteractionListener {

    companion object {
        // Constant representing the Shantay NPC identifier
        private const val SHANTAY_NPC = NPCs.SHANTAY_836
        // Constant representing the Shantay Pass ticket identifier
        private const val SHANTAY_PASS_TICKET = Items.SHANTAY_PASS_1854
        // Constant representing the coins identifier
        private const val COINS = Items.COINS_995
    }

    // Method to define interaction listeners
    override fun defineListeners() {

        // Listener for the interaction with the Shantay NPC when the player chooses to buy a pass
        on(SHANTAY_NPC, IntType.NPC, "buy-pass") { player, node ->
            // Make the NPC face the player for better interaction
            face(node.asNpc(), player, 2)
            // Check if the player has enough coins to buy the pass
            if (removeItem(player, Item(COINS, 5))) {
                // Send a dialogue to the player confirming the purchase of the Shantay Pass
                sendItemDialogue(player, SHANTAY_PASS_TICKET, "You purchase a Shantay Pass.")
                // Add the Shantay Pass to the player's inventory or drop it
                addItemOrDrop(player, SHANTAY_PASS_TICKET)
            } else {
                // Send a dialogue to the player indicating insufficient funds
                sendPlayerDialogue(player, "Sorry, I don't seem to have enough money.")
            }
            // Return true to indicate the interaction was handled
            return@on true
        }
    }
}