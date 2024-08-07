package content.region.kandarin.dialogue.ooglog

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Chargurr dialogue.
 */
@Initializable
class ChargurrDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.CHILD_NORMAL, "You scrawny, but look like you might make good dessert. You come back when fire lit and I introduce you to cooking spit.").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Somehow, this doesn't seem in-line with my personal development plan but, um, thanks for the offer.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CHARGURR_7054)
    }
}
