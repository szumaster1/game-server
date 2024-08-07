package content.region.desert.handlers

import core.api.consts.NPCs
import core.api.getUsedOption
import core.api.openNpcShop
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Nardah listeners.
 */
class NardahListeners : InteractionListener {

    companion object {
        private const val KAZEMDE_NPC = NPCs.KAZEMDE_3039
        private const val ROKUH_NPC = NPCs.ROKUH_3045
    }

    override fun defineListeners() {
        on(KAZEMDE_NPC, IntType.NPC, "talk-to", "trade") { player, _ ->
            if(getUsedOption(player) == "trade") {
                openNpcShop(player, KAZEMDE_NPC)
            }
            if(getUsedOption(player) == "talk-to") {
                player.dialogueInterpreter.open(KAZEMDE_NPC)
            }
            return@on true
        }

        on(ROKUH_NPC, IntType.NPC, "talk-to", "trade") { player, _ ->
            if(getUsedOption(player) == "trade") {
                openNpcShop(player, ROKUH_NPC)
            }
            if(getUsedOption(player) == "talk-to") {
                player.dialogueInterpreter.open(ROKUH_NPC)
            }
            return@on true
        }
    }
}