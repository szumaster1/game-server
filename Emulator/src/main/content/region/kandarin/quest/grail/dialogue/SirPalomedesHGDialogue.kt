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
 * Represents the Sir palomedes holy grail quest dialogue file.
 */
class SirPalomedesHGDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_PALOMEDES_3787)

        when (stage) {
            0 -> npcl(FacialExpression.HALF_GUILTY, "Hello there adventurer, what do you want of me?").also {
                if (getQuestStage(player!!, QuestName.HOLY_GRAIL) == 0 || isQuestComplete(player!!, QuestName.HOLY_GRAIL)) {
                    stage = 1
                } else if (getQuestStage(player!!, QuestName.HOLY_GRAIL) >= 10){
                    stage = 10
                }
            }

            1 -> npcl(FacialExpression.HALF_GUILTY, "After your help freeing Merlin, my help is the least I can offer as a man of honour.").also { stage++ }
            2 -> playerl(FacialExpression.HALF_GUILTY, "Nothing right now, but I'll bear it in mind. Thanks.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "I'd like some advice on finding the Grail.").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "Sorry, I cannot help you with that.").also { stage = END_DIALOGUE }

        }
    }

}
