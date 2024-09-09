package content.global.activity.oldman

import cfg.consts.NPCs
import core.api.getAttribute
import core.api.setAttribute
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

/**
 * Represents the Wise Old Man task dialogue.
 */
class WomDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        val item = WomDelivery.values().map { it.item }.toIntArray().random()
        val testAmount = RandomFunction.random(1,15)
        npc = NPC(NPCs.WISE_OLD_MAN_2253)
        when (stage) {
            0 -> if(getAttribute(player!!, WomTask.TASK_START, false)) {
                player("What did you ask me to do?").also { stage++ }
                setAttribute(player!!, "/save:${WomTask.TASK_START}", true)
            } else {
                player("Is there anything I can do for you?").also { stage++ }
            }
            1 -> npcl(FacialExpression.HALF_GUILTY, "I need " + " $testAmount " + WomDelivery.forId(item).toString().lowercase().replace("_", " ") + ".").also { setAttribute(player!!, WomTask.TASK_START, true); stage++ }
            2 -> options("Where can I get that?", "Right, I'll see you later.").also { stage++ }
            3 -> when (buttonID) {
                1 -> playerl(FacialExpression.HALF_GUILTY, "Where can I get that?").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "Right, I'll see you later.").also { stage = END_DIALOGUE }
            }
            4 -> npc(FacialExpression.HALF_GUILTY, "[MISSING DIALOGUE]").also { stage++ }
            5 -> playerl(FacialExpression.HALF_GUILTY, "Right, I'll see you later.").also { stage = END_DIALOGUE }

        }
    }
}

// I need some lengths of bronze wire to repair something. I still need 9.
// Try smelting some copper and tin ore to make bronze. Then hammer the bronze on an anvil to make wire.
