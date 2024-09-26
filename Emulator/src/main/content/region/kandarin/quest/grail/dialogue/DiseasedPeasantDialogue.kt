package content.region.kandarin.quest.grail.dialogue

import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Diseased peasant holy grail dialogue file.
 */
class DiseasedPeasantDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.PEASANT_214)

        when (stage) {
            0 -> npcl(FacialExpression.SAD, "Woe is me! Our crops are all failing... how shall I feed myself this winter?").also { stage = END_DIALOGUE }
        }
    }

}
