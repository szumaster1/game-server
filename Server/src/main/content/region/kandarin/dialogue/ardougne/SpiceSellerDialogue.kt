package content.region.kandarin.dialogue.ardougne

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Spice Seller dialogue.
 */
@Initializable
class SpiceSellerDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Runs the spice stall in the south-west corner of
     * the Ardougne marketplace in East Ardougne.
     * Location: 2659,3296
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Are you interested in buying or selling spice?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes.", "No.").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.SPICE_SELLER_572)
                }
                2 -> player(FacialExpression.HALF_GUILTY, "No, thanks.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPICE_SELLER_572)
    }
}
