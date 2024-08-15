package content.region.fremennik.handlers.waterbirth

import content.region.fremennik.dialogue.waterbrith.BardurExchangeDialogue
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.api.sendNPCDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Bardur exchange listener.
 */
class BardurExchangeListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling Bardur NPC exchange items.
         */
        onUseWith(IntType.NPC, FREMENNIK_EQUIPMENT, NPCs.BARDUR_2879) { player, _, _ ->
            if (!isQuestComplete(player, "Fremennik Trials")) {
                sendNPCDialogue(player, NPCs.BARDUR_2879, "I do not trust you outerlander, I will not accept your gifts, no matter what your intention...")
            } else {
                openDialogue(player, BardurExchangeDialogue())
            }
            return@onUseWith true
        }
    }

    companion object {
        val FREMENNIK_EQUIPMENT = intArrayOf(Items.FREMENNIK_HELM_3748, Items.FREMENNIK_BLADE_3757, Items.FREMENNIK_SHIELD_3758)
    }
}
