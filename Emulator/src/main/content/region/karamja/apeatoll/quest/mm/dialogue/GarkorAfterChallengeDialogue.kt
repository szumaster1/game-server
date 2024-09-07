package content.region.karamja.apeatoll.quest.mm.dialogue

import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression

/**
 * Represents the Garkor after challenge dialogue.
 */
class GarkorAfterChallengeDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> npcl(FacialExpression.AMAZED, "Well done on winning Awowogei trust. I overheard everything from here.").also { stage++ }
            1 -> npcl(FacialExpression.NEUTRAL, "However, you efforts may be in vain...").also { stage++ }
            2 -> playerl(FacialExpression.NEUTRAL, "What do you mean?").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "Progress in the caves has been slow. Whilst you were in Ardougne, Bunkwicket and Waymottin overheard a slightly disturbing conversation.").also { stage++ }
            4 -> playerl(FacialExpression.NEUTRAL, "Who was speaking? What was said?").also { stage++ }
            5 -> npcl(FacialExpression.NEUTRAL, "Listen closely whilst I narrate the details...").also {
                setQuestStage(player!!, "Monkey Madness", 50)
                content.region.karamja.apeatoll.quest.mm.cutscenes.DungeonPlanWithAwowogeiCutScene(player!!).start()
                stage++
            }

            6 -> {
                //showPopup "Monkey Madness: Chapter 4"
                //The Final Battle
                stage = 99
            }

            99 -> end()
        }
    }
}
