package content.region.fremennik.miscellania

import org.rs.consts.NPCs
import content.region.fremennik.miscellania.dialogue.FarmerFromundDialogue
import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import content.region.fremennik.miscellania.dialogue.FishermanFrodiDialogue

class MiscellaniaListener : InteractionListener{

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