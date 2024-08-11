package content.region.karamja.quest.monkeymadness.dialogue

import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression

/**
 * Garkor dialogue.
 */
class GarkorDialogue: DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> npcl(FacialExpression.AMAZED, "My my, Zooknock has outdone himself this time. You do look very much like a monkey you know.").also { stage++ }
            1 -> playerl(FacialExpression.NEUTRAL, "I know.").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "And by happy coincidence you appear to be just the right sort of monkey.").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "I need you now to seek audience with Awowogei. Claim you are an envoy from the monkeys of Karamja and are seeking an alliance.").also { stage++ }
            4 -> npcl(FacialExpression.NEUTRAL, "You must win his trust if we are to succeed.").also { stage++ }
            5 -> {
                end()
                setQuestStage(player!!, "Monkey Madness", 35)
            }
        }
    }
}
