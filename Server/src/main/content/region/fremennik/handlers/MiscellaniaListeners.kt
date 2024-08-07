package content.region.fremennik.handlers

import content.region.fremennik.dialogue.miscellania.FarmerFromundDialogue
import content.region.fremennik.dialogue.miscellania.FishermanFrodiDialogue
import core.api.consts.NPCs
import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Miscellania listeners.
 */
class MiscellaniaListeners : InteractionListener{

    override fun defineListeners() {

        on(NPCs.FISHERMAN_FRODI_1397, IntType.NPC, "talk-to") { player, node ->
            openDialogue(player, FishermanFrodiDialogue())
            return@on true
        }

        on(NPCs.FARMER_FROMUND_3917, IntType.NPC, "talk-to") { player, node ->
            openDialogue(player, FarmerFromundDialogue())
            return@on true
        }

    }
}