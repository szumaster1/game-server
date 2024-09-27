package content.region.fremennik.waterbirth.dungeon

import core.api.isQuestComplete
import core.api.openDialogue
import core.api.sendNPCDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.QuestName

class BardurExchangeListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling Bardur NPC exchange items.
         */
        onUseWith(IntType.NPC, exchangeItemIDs, NPCs.BARDUR_2879) { player, _, _ ->
            if (!isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
                sendNPCDialogue(player, NPCs.BARDUR_2879, "I do not trust you outerlander, I will not accept your gifts, no matter what your intention...")
            } else {
                openDialogue(player, BardurExchangeDialogue())
            }
            return@onUseWith true
        }
    }

    companion object {
        val exchangeItemIDs = intArrayOf(Items.FREMENNIK_HELM_3748, Items.FREMENNIK_BLADE_3757, Items.FREMENNIK_SHIELD_3758)
    }
}
