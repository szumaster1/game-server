package content.region.misc.keldagrim.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Ulifed dialogue.
 */
@Initializable
class UlifedDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_DEFAULT, "Say, aren't you the human who got arrested here the other day, by the boat?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Yes, but we cleared up the whole situation.").also { stage++ }
            2 -> npcl(FacialExpression.OLD_DEFAULT, "Right, right.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ULIFED_2193)
    }
}
