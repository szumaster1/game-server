package content.region.asgarnia.burthope.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Servant dialogue.
 */
@Initializable
class ServantDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage++ }
            1 -> npcl(FacialExpression.HALF_GUILTY, "Hi").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "Look, I'd better not talk. I'll get in trouble.").also { stage++ }
            3 -> npcl(FacialExpression.HALF_GUILTY, "If you want someone to show you round the castle ask Eohric, the Head Servant.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SERVANT_1081)
    }
}
