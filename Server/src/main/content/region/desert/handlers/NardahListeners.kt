package content.region.desert.handlers

import cfg.consts.NPCs
import core.api.getUsedOption
import core.api.openNpcShop
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the Nardah listeners.
 */
class NardahListeners : InteractionListener {

    companion object {
        // Constant for Kazemde NPC identifier
        private const val KAZEMDE_NPC = NPCs.KAZEMDE_3039
        // Constant for Rokuh NPC identifier
        private const val ROKUH_NPC = NPCs.ROKUH_3045
    }

    override fun defineListeners() {

        /*
         * Define listener for interactions with Kazemde.
         */

        on(KAZEMDE_NPC, IntType.NPC, "talk-to", "trade") { player, _ ->
            // Check if the player chose the "trade" option
            if(getUsedOption(player) == "trade") {
                // Open the NPC shop for Kazemde
                openNpcShop(player, KAZEMDE_NPC)
            }
            // Check if the player chose the "talk-to" option
            if(getUsedOption(player) == "talk-to") {
                // Open the dialogue interface for Kazemde
                player.dialogueInterpreter.open(KAZEMDE_NPC)
            }
            // Return true to indicate the event was handled
            return@on true
        }

        /*
         * Define listener for interactions with Rokuh.
         */

        on(ROKUH_NPC, IntType.NPC, "talk-to", "trade") { player, _ ->
            // Check if the player chose the "trade" option
            if(getUsedOption(player) == "trade") {
                // Open the NPC shop for Rokuh
                openNpcShop(player, ROKUH_NPC)
            }
            // Check if the player chose the "talk-to" option
            if(getUsedOption(player) == "talk-to") {
                // Open the dialogue interface for Rokuh
                player.dialogueInterpreter.open(ROKUH_NPC)
            }
            // Return true to indicate the event was handled
            return@on true
        }
    }
}