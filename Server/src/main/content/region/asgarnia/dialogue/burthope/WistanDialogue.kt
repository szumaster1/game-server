package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Wistan dialogue.
 */
@Initializable
class WistanDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Welcome to Burthorpe Supplies. Your last shop before heading north into the mountains!").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Would you like to buy something?").also { stage++ }
            3 -> options("Yes, please.", "No, thanks.").also { stage++ }
            4 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, please.").also { stage = 5 }
                2 -> playerl(FacialExpression.FRIENDLY, "No, thanks.").also { stage = END_DIALOGUE }
            }
            5 -> {
                end()
                openNpcShop(player, NPCs.WISTAN_1083)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WISTAN_1083)
    }
}
