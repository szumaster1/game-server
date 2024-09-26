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
 * Represents the Sir kay holy grail quest dialogue file.
 */
class SirKayHGDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_KAY_241)

        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Good morrow sirrah!").also {
                if (getQuestStage(player!!, QuestName.HOLY_GRAIL) == 0 || isQuestComplete(player!!, QuestName.HOLY_GRAIL)) {
                    stage = 1
                } else if (getQuestStage(player!!, QuestName.HOLY_GRAIL) >= 10){
                    stage = 10
                }
            }
            1 -> npcl(FacialExpression.NEUTRAL, "Sir Knight! Many thanks for your assistance in restoring Merlin to his former freedom!").also { stage++ }
            2 -> playerl(FacialExpression.NEUTRAL, "Hey, no problem.").also { stage = END_DIALOGUE }

            10 -> npcl(FacialExpression.NEUTRAL, "I hear you are questing for the Holy Grail?!").also { stage++ }
            11 -> playerl(FacialExpression.NEUTRAL, "That's right. Any hints?").also { stage++ }
            12 -> npcl(FacialExpression.NEUTRAL, "Unfortunately not, Sirrah.").also { stage = END_DIALOGUE }
        }
    }

}
