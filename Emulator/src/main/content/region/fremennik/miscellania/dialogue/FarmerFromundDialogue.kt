package content.region.fremennik.miscellania.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Farmer Fromund dialogue.
 */
class FarmerFromundDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.FARMER_FROMUND_3917)
        when(stage){
            0 -> npcl(FacialExpression.HALF_GUILTY, "Hey! This is the Queen's farm. You'll need her approval to make use of it.").also { stage = END_DIALOGUE }
        }
    }
}