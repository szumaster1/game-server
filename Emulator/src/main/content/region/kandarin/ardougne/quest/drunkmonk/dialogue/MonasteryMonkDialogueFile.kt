package content.region.kandarin.ardougne.quest.drunkmonk.dialogue

import core.api.getQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Monastery Monks dialogue file.
 */
class MonasteryMonkDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        var questStage = getQuestStage(player!!, QuestName.MONKS_FRIEND)
        if (questStage < 100) {
            when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "*yawn*").also { stage = END_DIALOGUE }
            }
        } else {
            when (stage) {
                0 -> npcl(FacialExpression.HAPPY, "Can't wait for the party!").also { stage = END_DIALOGUE }
            }
        }
    }
}
