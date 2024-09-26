package content.region.kandarin.quest.merlin.dialogue

import org.rs.consts.NPCs
import core.api.getQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Sir Tristram dialogue file.
 */
class SirTristramDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_TRISTRAM_243)
        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Hail Arthur, King of the Britons!").also { stage++ }
            1 -> playerl(FacialExpression.NEUTRAL, "Um... Hello.").also {
                if (getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) == 0) {
                    stage = 2
                } else if (getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) == 10) {
                    stage = 10
                } else if (getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) == 20 || getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) == 30) {
                    stage = 20
                } else if (getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) >= 40) {
                    stage = 40
                }
            }

            2 -> playerl(FacialExpression.NEUTRAL, "I'm looking for adventure! More specifically, some sort of quest.").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "...Then hail Arthur, King of Britons, like I just said.").also { stage++ }
            4 -> playerl(FacialExpression.NEUTRAL, "Oh. Ok.").also { stage++ }
            5 -> playerl(FacialExpression.NEUTRAL, "I thought you just had a weird way of saying hello is all.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.NEUTRAL, "Do you know much about breaking magical crystals?").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "Funnily enough...").also { stage++ }
            12 -> npcl(FacialExpression.NEUTRAL, "Absolutely nothing.").also { stage++ }
            13 -> playerl(FacialExpression.NEUTRAL, "Ok. Goodbye.").also { stage = END_DIALOGUE }

            20 -> playerl(FacialExpression.NEUTRAL, "I need to get into Mordred's Fort...").also { stage++ }
            21 -> npcl(FacialExpression.NEUTRAL, "Good luck with that!").also { stage = END_DIALOGUE }

            40 -> playerl(FacialExpression.FRIENDLY, "I need to find Excalibur...").also { stage = 21 }
        }
    }

}
