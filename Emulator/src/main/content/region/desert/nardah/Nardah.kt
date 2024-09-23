package content.region.desert.nardah

import org.rs.consts.NPCs
import core.api.getUsedOption
import core.api.openNpcShop
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the Nardah listeners.
 */
class Nardah : InteractionListener {

    override fun defineListeners() {

        /*
         * Define listener for interactions with Kazemde & Rokuh.
         */

        on(intArrayOf(NPCs.KAZEMDE_3039, NPCs.ROKUH_3045), IntType.NPC, "talk-to", "trade") { player, node ->
            if(getUsedOption(player) == "trade") {
                openNpcShop(player, node.id)
            }
            if(getUsedOption(player) == "talk-to") {
                player.dialogueInterpreter.open(node.id)
            }
            return@on true
        }

    }
}