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
 * Represents the Sir bedivere holy grail quest dialogue file.
 */
class SirBedivereHGDialogue: DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_BEDIVERE_242)

        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "May I help you?").also {
                if (getQuestStage(player!!, QuestName.HOLY_GRAIL) == 0 || isQuestComplete(player!!, QuestName.HOLY_GRAIL)) {
                    stage = 2
                } else if (getQuestStage(player!!, QuestName.HOLY_GRAIL) >= 10) {
                    stage = 10
                }
            }

            2 -> npcl(FacialExpression.NEUTRAL, "All Knights of the Round thank you for your assistance in this trying time for us.").also { stage = END_DIALOGUE }

            10 -> npcl(FacialExpression.FRIENDLY, "You are looking for the Grail now adventurer?").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Absolutely.").also { stage++ }
            12 -> npcl(FacialExpression.FRIENDLY, "The best of luck to you! Make the name of Camelot proud, and bring it back to us.").also { stage = END_DIALOGUE }
        }
    }

}
