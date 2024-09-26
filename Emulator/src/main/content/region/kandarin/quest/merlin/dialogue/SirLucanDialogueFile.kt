package content.region.kandarin.quest.merlin.dialogue

import org.rs.consts.NPCs
import core.api.getQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Sir Lucan dialogue file.
 */
class SirLucanDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_LUCAN_245)

        when (stage) {
            0 -> {
                npcl(FacialExpression.NEUTRAL, "Hello there adventurer.").also {

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
            1 -> playerl(FacialExpression.NEUTRAL, "I'm looking for a quest...").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "Talk to the King then adventurer. He is always looking for men of bravery to aid him in his actions.").also { stage = END_DIALOGUE }


            10 -> {
                playerl(FacialExpression.NEUTRAL, "Any suggestions on freeing Merlin?")
                stage++
            }
            11 -> {
                npcl(FacialExpression.NEUTRAL, "I've tried all the weapons I can find, yet none are powerful enough to break his crystal prison...")
                stage++
            }
            12 -> {
                playerl(FacialExpression.NEUTRAL, "Excalibur eh? Where would I find that?")
                stage++
            }
            13 -> {
                npcl(FacialExpression.NEUTRAL, "The Lady of the Lake is looking after it I believe... but I know not where she resides currently.")
                stage++
            }
            14 -> {
                playerl(FacialExpression.NEUTRAL, "Thanks. I'll try and find someone who does.")
                stage = END_DIALOGUE
            }

            20 -> playerl(FacialExpression.NEUTRAL, "I need to get into Mordred's Fortress.").also { stage++ }
            21 -> npcl(FacialExpression.NEUTRAL, "So... you think Mordred's behind this? I'm afraid I don't have any suggestions...").also { stage++ }
            22 -> playerl(FacialExpression.NEUTRAL, "Thanks. I'll try and find someone who does.").also { stage = END_DIALOGUE }

            40 -> {
                playerl(FacialExpression.NEUTRAL, "I need to find Excalibur.").also { stage++ }
            }
            41 -> {
                npcl(FacialExpression.FRIENDLY, "I'm afraid I don't have any suggestions...").also { stage++ }
                stage = 14
            }
        }
    }

}
