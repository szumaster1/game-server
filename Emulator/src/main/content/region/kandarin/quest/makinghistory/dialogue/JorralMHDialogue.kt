package content.region.kandarin.quest.makinghistory.dialogue

import content.region.kandarin.quest.makinghistory.handlers.MakingHistoryUtils
import org.rs.consts.NPCs
import core.api.setQuestStage
import core.api.setVarbit
import core.api.updateQuestTab
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Jorral dialogue file.
 */
class JorralMHDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.JORRAL_2932)
        when(stage){
            0 -> npcl(FacialExpression.HALF_GUILTY, "If all goes well, I hope to be able to turn it into a museum as a monument to the area's history. What do you think?").also { stage++ }
            1 -> options("Ok, I'll make a stand for history!", "I don't care about some dusty building").also { stage++ }
            2 -> when (buttonID) {
                1 -> playerl(FacialExpression.HALF_GUILTY, "OK, I will make a stand for history!").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "I don't care about some dusty building").also { stage = 4 }
            }
            3 -> npcl(FacialExpression.HAPPY, "Oh, thank you so much, you really are my saviour!").also {
                setQuestStage(player!!, QuestName.MAKING_HISTORY, 1)
                setVarbit(player!!, MakingHistoryUtils.PROGRESS, 1, true)
                updateQuestTab(player!!)
                stage = END_DIALOGUE
            }
            4 -> npc("It's doomed. DOOMED!").also { stage = END_DIALOGUE }
        }
    }
}
