package content.dialogue.varrock

import core.api.lock
import core.api.sendNPCDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.tools.BLUE
import core.tools.END_DIALOGUE

/**
 * Represents the Knockat door dialogue.
 */
class KnockatDoorDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {

        val npcId = if (player!!.location.x == 3182) 45 else 44
        when (stage) {
            0 -> player("I don't think I'm ever going to be allowed in there.").also { stage++ }
            1 -> {
                lock(player!!, 3)
                interpreter!!.sendPlainMessage(true, "", BLUE + "Knock knock...")
                Pulser.submit(object : Pulse(3, player) {
                    override fun pulse(): Boolean {
                        sendNPCDialogue(player!!, npcId, "Who's there?", FacialExpression.ANNOYED).also { stage++ }
                        return true
                    }
                })
            }

            2 -> options("I'm " + player!!.username + ". Please let me in.", "Boo.", "Kanga.", "Thank.", "Doctor.").also { stage++ }
            3 -> when (buttonID) {
                1 -> player(FacialExpression.NEUTRAL, "I'm " + player!!.username + ". Please let me in.").also { stage = 10 }
                2 -> player(FacialExpression.NEUTRAL, "Boo.").also { stage = 20 }
                3 -> player(FacialExpression.NEUTRAL, "Kanga.").also { stage = 30 }
                4 -> player(FacialExpression.NEUTRAL, "Thank.").also { stage = 40 }
                5 -> player(FacialExpression.NEUTRAL, "Doctor.").also { stage = 50 }
            }

            10 -> sendNPCDialogue(player!!, npcId, "No. Staff only beyond this point. You can't come in here.", FacialExpression.ANNOYED).also { stage = END_DIALOGUE }

            20 -> sendNPCDialogue(player!!, npcId, "Boo who?").also { stage++ }
            21 -> player(FacialExpression.NEUTRAL, "There's no need to cry!").also { stage++ }
            22 -> sendNPCDialogue(player!!, npcId, "What? I'm not... oh, just go away!", FacialExpression.ANNOYED).also { stage = END_DIALOGUE }

            30 -> sendNPCDialogue(player!!, npcId, "Kanga who?").also { stage++ }
            31 -> player(FacialExpression.NEUTRAL, "No, 'kangaroo'.").also { stage++ }
            32 -> sendNPCDialogue(player!!, npcId, "Stop messing about and go away!", FacialExpression.ANNOYED).also { stage = END_DIALOGUE }

            40 -> sendNPCDialogue(player!!, npcId, "Thank who?").also { stage++ }
            41 -> player(FacialExpression.NEUTRAL, "You're welcome!").also { stage++ }
            42 -> sendNPCDialogue(player!!, npcId, "Stop it!", FacialExpression.ANNOYED).also { stage = END_DIALOGUE }
            50 -> sendNPCDialogue(player!!, npcId, "Doctor. wh.. hang on, I'm not falling for that one again! Go away.", FacialExpression.ANNOYED).also { stage = END_DIALOGUE }
        }
    }
}
