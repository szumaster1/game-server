package content.region.kandarin.quest.grail.dialogue

import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Healthy peasant holy grail dialogue file.
 */
class HealthyPeasantHolyGrailDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.PEASANT_215)

        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Oh happy day! Suddenly our crops are growing again! It'll be a bumper harvest this year!").also { stage = END_DIALOGUE }
        }
    }

}
