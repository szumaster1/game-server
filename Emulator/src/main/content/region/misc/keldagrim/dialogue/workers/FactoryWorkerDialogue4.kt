package content.region.misc.keldagrim.dialogue.workers

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Factory worker 4 dialogue.
 */
@Initializable
class FactoryWorkerDialogue4(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Who owns this factory?").also { stage++ }
            1 -> npcl(FacialExpression.OLD_DEFAULT, "The Consortium does and that's all you need to know.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "But what company? I thought there were all these different companies?").also { stage++ }
            3 -> npcl(FacialExpression.OLD_DEFAULT, "Oh yes, all the major companies own this plant. It's too vital to be in the hands of one company alone.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "And what exactly are you doing here?").also { stage++ }
            5 -> npcl(FacialExpression.OLD_ANGRY1, "I tire of these questions. Let me get back to work!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FACTORY_WORKER_2175)
    }
}
