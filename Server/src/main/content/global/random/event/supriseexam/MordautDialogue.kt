package content.global.random.event.supriseexam

import core.api.getAttribute
import core.api.openDialogue
import core.api.openInterface
import core.game.component.Component
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.utilities.END_DIALOGUE

class MordautDialogue(
    val examComplete: Boolean, val questionCorrect: Boolean = false, val fromInterface: Boolean = false
) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        if (examComplete) {
            if (getAttribute(player!!, SupriseExamUtils.SE_DOOR_KEY, -1) == -1) {
                SupriseExamUtils.pickRandomDoor(player!!)
            }
            val door = getAttribute(player!!, SupriseExamUtils.SE_DOOR_KEY, -1)
            val doortext = when (door) {
                2188 -> "red cross"
                2189 -> "blue star"
                2192 -> "purple circle"
                2193 -> "green square"
                else -> "REEEEEEEEEEEEEEEE"
            }
            npc("Please exit through the $doortext door.")
            stage = END_DIALOGUE
        } else if (fromInterface) {

            if (questionCorrect) {
                if (getAttribute(player!!, SupriseExamUtils.SE_KEY_CORRECT, 0) == 3) {
                    openDialogue(player!!, MordautDialogue(true), npc!!)
                } else {
                    when (stage++) {
                        0 -> npc("Excellent work!")
                        1 -> npc("Now for another...")
                        2 -> {
                            end()
                            openInterface(player!!, SupriseExamUtils.INTERFACE)
                        }
                    }
                }
            } else {
                when (stage++) {
                    0 -> npc("I'm afraid that isn't correct.")
                    1 -> npc("Now for another...")
                    2 -> {
                        end()
                        openInterface(player!!, SupriseExamUtils.INTERFACE)
                    }
                }
            }

        } else {
            when (stage++) {
                0 -> npc("Please answer these questions for me.")
                1 -> {
                    end()
                    openInterface(player!!, SupriseExamUtils.INTERFACE)
                }
            }
        }
    }

    override fun npc(vararg messages: String?): Component? {
        return super.npc(FacialExpression.OLD_NORMAL, *messages)
    }
}
