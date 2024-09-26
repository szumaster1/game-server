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
 * Represents the Sir pellease holy grail quest dialogue file.
 */
class SirPelleaseHGDialogue  : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_PELLEAS_244)

        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Greetings to the court of King Arthur!").also {
                if (getQuestStage(player!!, QuestName.HOLY_GRAIL) == 0 || isQuestComplete(player!!, QuestName.HOLY_GRAIL)) {
                    stage = 1
                } else if (getQuestStage(player!!, QuestName.HOLY_GRAIL) >= 10) {
                    stage = 10
                }
            }
            1 -> npcl(FacialExpression.NEUTRAL, "You are a very talented Knight indeed to have freed Merlin so quickly. You have all of our gratitude.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.NEUTRAL, "Any suggestions on finding the Grail?").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "My best guess would be some sort of spell. Merlin is our magic expert. Ask him?").also { stage++ }
            12 -> npcl(FacialExpression.NEUTRAL, "Although having said that, I believe Galahad found its location once...").also { stage++ }
            13 -> playerl(FacialExpression.NEUTRAL, "Really? Know where I can find him?").also { stage++ }
            14 -> npcl(FacialExpression.NEUTRAL, "I'm afraid not. He left here many moons ago and I know not where he went.").also { stage = END_DIALOGUE }

        }
    }

}
