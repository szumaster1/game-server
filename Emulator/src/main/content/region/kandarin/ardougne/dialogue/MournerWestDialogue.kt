package content.region.kandarin.ardougne.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.tools.END_DIALOGUE

/**
 * Represents the Mourner dialogue.
 */
class MournerWestDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Mourner is located outside West Ardougne near the gate.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Good day. Are you in need of assistance?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Yes, but I don't think you can help.").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "You will be surprised at how much help the brute force of the Guard can be.").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Well I'll be sure to ask if I'm in need of some muscle.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MOURNER_719)
    }
}