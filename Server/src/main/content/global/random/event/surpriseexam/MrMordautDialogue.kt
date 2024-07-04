package content.global.random.event.surpriseexam

import core.api.consts.NPCs
import core.api.getAttribute
import core.api.openDialogue
import core.api.openInterface
import core.game.component.Component
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.utilities.END_DIALOGUE

class MrMordautDialogue(
    val examComplete: Boolean, val questionCorrect: Boolean = false, val fromInterface: Boolean = false
) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MR_MORDAUT_6117)
        if (examComplete) {
            if (getAttribute(player!!, SurpriseExamUtils.SE_DOOR_KEY, -1) == -1) {
                SurpriseExamUtils.pickRandomDoor(player!!)
            }
            val door = getAttribute(player!!, SurpriseExamUtils.SE_DOOR_KEY, -1)
            val doortext = when (door) {
                2188 -> "red cross"
                2189 -> "blue star"
                2192 -> "purple circle"
                2193 -> "green square"
                else -> ""
            }
            when(stage++) {
                0 -> npc("WELL DONE!","You've proven your aptitude for pattern recognition.","You'll receive a prize when you leave the classroom.")
                1 -> npcl("To exit, use the $doortext door in the north of the school room.").also { stage = END_DIALOGUE }
            }
        } else if (fromInterface) {

            if (questionCorrect) {
                if (getAttribute(player!!, SurpriseExamUtils.SE_KEY_CORRECT, 0) == 3) {
                    openDialogue(player!!, MrMordautDialogue(true), npc!!)
                } else {
                    when (stage) {
                        0 -> {
                            if (getAttribute(player!!, SurpriseExamUtils.SE_KEY_CORRECT, 0) == 1) {
                                npc("Finally, a pupil using his brains rather than trying to", "eat them. Next question.")
                                stage++
                            } else stage = 1
                        }
                        1 -> npc("Now for another...").also { stage++ }
                        // Examine the hint on the right and find three objects that are related to it.
                        2 -> {
                            end()
                            openInterface(player!!, SurpriseExamUtils.INTERFACE)
                        }
                    }
                }
            } else {
                when (stage++) {
                    0 -> npc("I'm afraid that isn't correct.")
                    1 -> npc("Now for another...")
                    2 -> {
                        end()
                        openInterface(player!!, SurpriseExamUtils.INTERFACE)
                    }
                }
            }

        } else {
            /*
             * If player log out: "Let's continue, "${player!!.username}"
             */
            when (stage++) {
                0 -> npcl("Ah, It's you, ${player!!.username}. You've been slacking in your studies, so it's time for an exam.")
                1 -> npc("There are two types of question. The first type","requires you to find the next object in a pattern.")
                2 -> npc("In the second type, I will present you with fifteen cards","and a hint. Discover three cards related to the hint,","select them and confirm.")
                3 -> npc("Pick the object that should come next in the pattern.")
                4 -> {
                    end()
                    openInterface(player!!, SurpriseExamUtils.INTERFACE)
                }
            }
        }
    }

    override fun npc(vararg messages: String?): Component? {
        return super.npc(FacialExpression.OLD_NORMAL, *messages)
    }
}
