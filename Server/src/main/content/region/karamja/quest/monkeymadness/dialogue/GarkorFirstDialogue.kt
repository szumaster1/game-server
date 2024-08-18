package content.region.karamja.quest.monkeymadness.dialogue

import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression

/**
 * Represents the Garkor first dialogue.
 */
class GarkorFirstDialogue: DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> playerl(FacialExpression.NEUTRAL, "Hello?").also { stage++ }
            1 -> npcl(FacialExpression.NEUTRAL, "A fine day you have chosen to visit this hellish island, human.").also { stage++ }
            2 -> playerl(FacialExpression.NEUTRAL, "Good day to you too Sergeant. I've been sent by your King Narnode to -").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "Investigate the circumstances surrounding the mysterious disappearance of my squad. Yes, I know...").also { stage++ }
            4 -> playerl(FacialExpression.NEUTRAL, "How did you know that?").also { stage++}
            5 -> npcl(FacialExpression.NEUTRAL, "The King and I are still in communication, albeit sporadic. I decided I need a human of your calibre to assist me. It is pleasing to see you are still alive.").also { stage++ }
            6 -> playerl(FacialExpression.NEUTRAL, "Why do you need a human?").also { stage++}
            7 -> npcl(FacialExpression.NEUTRAL, "There is more going on than meets your eye, human. Did you not find it strange that an entire squad be sent to decomission a shipyard?").also { stage++ }
            8 -> playerl(FacialExpression.NEUTRAL, "Well -").also { stage++}
            9 -> npcl(FacialExpression.NEUTRAL, "Indeed. But there are more pressing matters at hand. Three of my squad have been captured and placed in the jail. They are watched over by somewhat overpowering guards.").also { stage++ }
            10 -> npcl(FacialExpression.NEUTRAL, "Before we can resume our original mission we must rescue them.").also { stage++ }
            11 -> playerl(FacialExpression.NEUTRAL, "I know about the guards - I had to sneak out between the change of shifts.").also { stage++}
            12 -> npcl(FacialExpression.NEUTRAL, "Trust me; we too have considered this, but whilst it is possible for one, it is near impossible for three.").also { stage++ }
            13 -> npcl(FacialExpression.NEUTRAL, "We have considered many things. I have my squad mage and sappers working below us right now. My assassin Karam, is operating in the village itself.").also { stage++ }
            14 -> npcl(FacialExpression.NEUTRAL, "I remain here so that I may overhear Awowogei's plans.").also { stage++ }
            15 -> {
                end()
                setQuestStage(player!!, "Monkey Madness", 30)
            }
        }
    }
}
