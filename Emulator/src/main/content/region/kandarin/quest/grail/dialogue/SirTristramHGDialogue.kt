package content.region.kandarin.quest.grail.dialogue

import core.api.getQuestStage
import core.api.isQuestComplete
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Sir tristram holy grail quest dialogue file.
 */
class SirTristramHGDialogue: DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_TRISTRAM_243)

        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Hail Arthur, King of the Britons!").also { stage++ }
            1 -> playerl(FacialExpression.NEUTRAL, "Um... Hello.").also {
                if (getQuestStage(player!!, QuestName.HOLY_GRAIL) == 0 || isQuestComplete(player!!, QuestName.HOLY_GRAIL)) {
                    stage = 2
                } else if (getQuestStage(player!!, QuestName.HOLY_GRAIL) >= 10) {
                    stage = 10
                }
            }
            2 -> npcl(FacialExpression.NEUTRAL, "Thanks for freeing Merlin.").also { stage++ }
            3 -> playerl(FacialExpression.NEUTRAL, "No problem. It was easy.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.NEUTRAL, "I am seeking the Grail...").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "Good luck with that!").also { stage = END_DIALOGUE }
        }
    }

}
