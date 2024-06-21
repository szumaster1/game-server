package content.region.fremennik.handlers.waterbirth

import content.region.fremennik.dialogue.waterbrith.BardurExchangeDialogue
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.api.sendNPCDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class BardurExchangeListener : InteractionListener {

    companion object {
        private val FREMENNIK_EQUIPMENT = intArrayOf(Items.FREMENNIK_HELM_3748, Items.FREMENNIK_BLADE_3757, Items.FREMENNIK_SHIELD_3758)
        private const val BARDUR_NPC = NPCs.BARDUR_2879
    }
    override fun defineListeners() {
        onUseWith(IntType.NPC, FREMENNIK_EQUIPMENT, BARDUR_NPC) { player, _, _ ->
            if (!isQuestComplete(player, "Fremennik Trials")) {
                sendNPCDialogue(player, BARDUR_NPC, "I do not trust you outerlander, I will not accept your gifts, no matter what your intention...")
            } else {
                openDialogue(player, BardurExchangeDialogue())
            }
            return@onUseWith true
        }
    }
}