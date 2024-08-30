package content.region.karamja.quest.mm.dialogue

import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression

/**
 * Represents the Garkor after battle dialogue.
 */
class GarkorAfterBattleDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> npcl(FacialExpression.AMAZED, "Well done, human! That was a most impressive display of skill.").also { stage++ }
            1 -> playerl(FacialExpression.GUILTY, "Thank you").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "You should report to king Narnode immediately. Tell him that the 10th squad still survives and has suffered no casualries.").also { stage++ }
            3 -> playerl(FacialExpression.NEUTRAL, "Rest assured, I will do so.").also { stage++ }
            4 -> playerl(FacialExpression.NEUTRAL, "How do I leave this place?").also { stage++ }
            5 -> npcl(FacialExpression.NEUTRAL, "Speak to Zooknock. He will arrange for you to leave.").also { stage++ }
            6 -> {
                end()
                setQuestStage(player!!, "Monkey Madness", 96)
            }
        }
    }
}
