package content.region.misthalin.dialogue.dorgeshuun

import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import cfg.consts.NPCs

/**
 * Represents the Zenkog dialogue.
 */
@Initializable
class ZenkogDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_NORMAL, "Wall beast fingers! How about a tasty snack of wall beast fingers?").also { stage++ }
            1 -> options("Yes please.", "No thanks.").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes please.").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "No thanks.").also { stage = 4 }
            }
            3 -> {
                end()
                openNpcShop(player, NPCs.ZENKOG_5797)
            }
            4 -> npcl(FacialExpression.OLD_NORMAL, "Have a good day!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ZENKOG_5797)
    }
}
