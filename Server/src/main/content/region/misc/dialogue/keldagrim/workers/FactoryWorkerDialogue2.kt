package content.region.misc.dialogue.keldagrim.workers

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE
import core.utilities.START_DIALOGUE

@Initializable
class FactoryWorkerDialogue2(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Are you okay?").also { stage++ }
            1 -> npcl(FacialExpression.OLD_ANGRY1, "Don't I look okay?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "If you were any shorter you wouldn't exist.").also { stage++ }
            3 -> npcl(FacialExpression.OLD_ANGRY1, "Very funny, human.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FACTORY_WORKER_2173)
    }
}
