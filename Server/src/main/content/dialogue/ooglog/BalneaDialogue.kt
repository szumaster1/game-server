package content.dialogue.ooglog

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Balnea dialogue.
 */
@Initializable
class BalneaDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi there!").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "I'm ever so busy at the moment; please come back after the grand opening.").also { stage++ }
            2 -> playerl(FacialExpression.HALF_ASKING, "What grand reopening?").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "I'm sorry, I really can't spare the time to talk to you.").also { stage++ }
            4 -> playerl(FacialExpression.THINKING, "Uh, sure.").also { stage = END_DIALOGUE }
        }

        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BALNEA_7047)
    }

}
