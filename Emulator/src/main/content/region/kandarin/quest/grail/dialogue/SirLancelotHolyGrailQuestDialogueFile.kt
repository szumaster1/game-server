package content.region.kandarin.quest.grail.dialogue

import content.region.kandarin.quest.grail.HolyGrail
import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Sir lancelot holy grail quest dialogue file.
 */
class SirLancelotHolyGrailQuestDialogueFile  : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_LANCELOT_239)

        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Greetings! I am Sir Lancelot, the greatest Knight in the land! What do you want?").also {
                if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) == 0 || isQuestComplete(player!!, HolyGrail.HOLY_GRAIL)) {
                    stage = 1
                } else {
                    stage = 10
                }
            }

            1 -> npcl(FacialExpression.NEUTRAL, "Hmmm. I heard you freed Merlin. Either you're better than you look or you got lucky. I think the latter.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "I am questing for the Holy Grail.").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "The Grail? Ha! Frankly, little man, you're not in that league.").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Why do you say that?").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "You got lucky with freeing Merlin but there's no way a puny wannabe like you is going to find the Holy Grail where so many others have failed.").also { stage++ }
            14 -> playerl(FacialExpression.ANGRY, "We'll see about that.").also { stage = END_DIALOGUE }
        }
    }

}
