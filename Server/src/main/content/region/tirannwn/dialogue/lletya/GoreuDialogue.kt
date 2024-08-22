package content.region.tirannwn.dialogue.lletya

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Goreu dialogue.
 */
@Initializable
class GoreuDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Good day, can I help you?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "No thanks I'm just watching the world go by.").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Well I can think of no better place to do it, it is beautiful here is it not?").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Indeed it is.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GOREU_2363)
    }
}
