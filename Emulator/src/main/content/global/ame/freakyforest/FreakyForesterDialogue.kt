package content.global.ame.freakyforest

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.tools.END_DIALOGUE

/**
 * Represents the Freaky Forester dialogue.
 */
class FreakyForesterDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        if (removeItem(player!!, Items.RAW_PHEASANT_6179) && !getAttribute(player!!, FreakyForesterUtils.freakComplete, false)) {
            npcl(FacialExpression.NEUTRAL, "That's not the right one.").also { stage = END_DIALOGUE }
            setAttribute(player!!, FreakyForesterUtils.pheasantKilled, false)
        } else if (removeItem(player!!, Items.RAW_PHEASANT_6178) || getAttribute(player!!,
                FreakyForesterUtils.freakComplete, false)) {
            npcl(FacialExpression.NEUTRAL, "Thanks, ${player!!.username}, you may leave the area now.").also { stage = END_DIALOGUE }
            sendChat(findNPC(FreakyForesterUtils.freakNpc)!!, "Thanks, ${player!!.username}, you may leave the area now.")
            setAttribute(player!!, FreakyForesterUtils.freakComplete, true)
        } else when (getAttribute(player!!, FreakyForesterUtils.freakTask, -1)) {
            NPCs.PHEASANT_2459 -> sendNPCDialogue(player!!, FreakyForesterUtils.freakNpc, "Hey there ${player!!.username}. Can you kill the one tailed pheasant please. Bring me the raw pheasant when you're done.").also { stage = END_DIALOGUE }
            NPCs.PHEASANT_2460 -> sendNPCDialogue(player!!, FreakyForesterUtils.freakNpc, "Hey there ${player!!.username}. Can you kill the two tailed pheasant please. Bring me the raw pheasant when you're done.").also { stage = END_DIALOGUE }
            NPCs.PHEASANT_2461 -> sendNPCDialogue(player!!, FreakyForesterUtils.freakNpc, "Hey there ${player!!.username}. Can you kill the three tailed pheasant please. Bring me the raw pheasant when you're done.").also { stage = END_DIALOGUE }
            NPCs.PHEASANT_2462 -> sendNPCDialogue(player!!, FreakyForesterUtils.freakNpc, "Hey there ${player!!.username}. Can you kill the four tailed pheasant please. Bring me the raw pheasant when you're done.").also { stage = END_DIALOGUE }
        }
    }
}
