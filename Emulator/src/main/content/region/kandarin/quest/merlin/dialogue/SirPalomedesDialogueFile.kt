package content.region.kandarin.quest.merlin.dialogue

import org.rs.consts.NPCs
import core.api.getQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Sir Palomedes dialogue file.
 */
class SirPalomedesDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_PALOMEDES_3787)

        when (stage) {
            0 -> {
                npcl(FacialExpression.NEUTRAL, "Hello there adventurer, what do you want of me?").also {
                    if (getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) == 0) {
                        stage = 1
                    } else if (getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) == 10) {
                        stage = 10
                    } else if (getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) == 20 || getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) == 30) {
                        stage = 20
                    } else if (getQuestStage(player!!, QuestName.MERLINS_CRYSTAL) >= 40) {
                        stage = 40
                    }
                }
            }
            1 -> playerl(FacialExpression.NEUTRAL, "I'd like some advice on finding a quest.").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "I do not know of any myself... but it would perhaps be worth your while asking the King if he has any tasks for you.").also { stage = END_DIALOGUE }
            10 -> playerl(FacialExpression.NEUTRAL, "I'd like some advice on breaking that Crystal Merlin's trapped in.").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "Sorry, I cannot help you with that.").also { stage = END_DIALOGUE }
            20 -> playerl(FacialExpression.NEUTRAL, "I'd like some advice on breaking into Mordred's fort.").also { stage++ }
            21 -> npcl(FacialExpression.NEUTRAL, "Sorry, I cannot help you with that.").also { stage = END_DIALOGUE }
            40 -> playerl(FacialExpression.NEUTRAL, "I'd like some advice on finding Excalibur.").also { stage = 11 }
        }
    }

}
