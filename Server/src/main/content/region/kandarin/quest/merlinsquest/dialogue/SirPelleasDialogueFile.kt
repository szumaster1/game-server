package content.region.kandarin.quest.merlinsquest.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Sir pelleas dialogue file
 *
 * @constructor Sir pelleas dialogue file
 */
class SirPelleasDialogueFile  : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_PELLEAS_244)
        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Greetings to the court of King Arthur!").also {
                if (getQuestStage(player!!, "Merlin's Crystal") == 0) {
                    stage = 1
                } else if (getQuestStage(player!!, "Merlin's Crystal") == 10) {
                    stage = 10
                } else if (getQuestStage(player!!, "Merlin's Crystal") == 20 || getQuestStage(player!!, "Merlin's Crystal") == 30) {
                    stage = 20
                } else if (getQuestStage(player!!, "Merlin's Crystal") >= 40){
                    stage = 40
                }
            }
            1 -> playerl(FacialExpression.NEUTRAL, "Hello. I'm looking for a quest. Who should I talk to?").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "King Arthur will let you know. I believe he has a quest at the moment.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.NEUTRAL, "Any suggestions on freeing Merlin?").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "My best guess would be some sort of magic. Unfortunately Merlin is our magic expert.").also { stage++ }
            12 -> playerl(FacialExpression.NEUTRAL, "Ok, well, thanks anyway.").also { stage = END_DIALOGUE }

            20 -> playerl(FacialExpression.NEUTRAL, "Any suggestions on getting into Mordred's fort?").also { stage++ }
            21 -> npcl(FacialExpression.NEUTRAL, "My best guess would be using magic. Unfortunately Merlin is our magic expert.").also { stage++ }
            22 -> playerl(FacialExpression.NEUTRAL, "Ok, well, thanks anyway.").also { stage = END_DIALOGUE }

            40 -> playerl(FacialExpression.NEUTRAL, "Any suggestions on finding Excalibur?").also { stage++ }
            41 -> npcl(FacialExpression.NEUTRAL, "My best guess would be some sort of spell. Unfortunately Merlin is our magic expert.").also { stage = 22 }

        }
    }

}
