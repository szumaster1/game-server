package content.region.misc.keldagrim.dialogue

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Gulldamar dialogue.
 */
@Initializable
class GulldamarDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_DEFAULT, "Finest silver in all of Keldagrim, come and see!").also { stage++ }
            1 -> options("Right, what do you have?", "Not interested, thanks.").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Right, what do you have?").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "Not interested, thanks.").also { stage = 5 }
            }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Silver, what else!").also { stage++ }
            4 -> {
                end()
                openNpcShop(player, NPCs.GULLDAMAR_2159)
            }
            5 -> playerl(FacialExpression.FRIENDLY, "No thanks.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GULLDAMAR_2159)
    }

}
