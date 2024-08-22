package content.region.fremennik.handlers

import content.region.fremennik.dialogue.miscellania.FarmerFromundDialogue
import content.region.fremennik.dialogue.miscellania.FishermanFrodiDialogue
import cfg.consts.NPCs
import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the Miscellania listeners.
 */
class MiscellaniaListeners : InteractionListener{

    override fun defineListeners() {

        // Define listener for talking to Fisherman Frodi NPC.
        on(NPCs.FISHERMAN_FRODI_1397, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, FishermanFrodiDialogue())
            return@on true
        }

        // Define listener for talking to Farmer Fromund NPC.
        on(NPCs.FARMER_FROMUND_3917, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, FarmerFromundDialogue())
            return@on true
        }

    }
}