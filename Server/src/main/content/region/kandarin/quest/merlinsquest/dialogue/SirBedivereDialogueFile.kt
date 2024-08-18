package content.region.kandarin.quest.merlinsquest.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Sir bedivere dialogue file.
 */
class SirBedivereDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_BEDIVERE_242)

        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "May I help you?").also {
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
            1 -> playerl(FacialExpression.NEUTRAL, "I'm really just looking for a quest...").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "Fortune favours us both then adventurer. I suggest you go and speak to King Arthur.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.NEUTRAL, "Merlin's in a crystal. Little help?").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "That is what we were hoping for from you, adventurer!").also { stage++ }
            12 -> playerl(FacialExpression.NEUTRAL, "Hmmm. Well, ok, thanks anyway.").also { stage = END_DIALOGUE }

            20 -> playerl(FacialExpression.NEUTRAL, "I don't suppose you have any idea how to break into Mordred's fort do you?").also { stage++ }
            21 -> npcl(FacialExpression.NEUTRAL, "I am afraid not. Would that we could! Mordred and his cronies have been thorns in our side for far too long already!").also { stage++ }
            22 -> playerl(FacialExpression.NEUTRAL, "Ok. Thanks. See you later!").also { stage++ }
            23 -> npcl(FacialExpression.NEUTRAL, "Take care adventurer, Mordred is an evil and powerful foe.").also { stage = END_DIALOGUE }

            40 -> playerl(FacialExpression.NEUTRAL, "Know anything about Excalibur?").also { stage++ }
            41 -> npcl(FacialExpression.NEUTRAL, "Um... it's a really good sword?").also { stage++ }
            42 -> playerl(FacialExpression.NEUTRAL, "Know where I can find it?").also { stage++ }
            43 -> npcl(FacialExpression.NEUTRAL, "Nope, sorry.").also { stage = END_DIALOGUE }
        }

    }

}
