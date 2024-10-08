package content.region.asgarnia.portsarim.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Grum dialogue.
 */
@Initializable
class GrumDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Grum is a man who runs Grum's Gold Exchange in Port Sarim.
     * He buys and sells unenchanted rings, amulets and necklaces
     * from sapphire up to diamond.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Would you like to buy or sell some gold jewellery?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please.", "No, I'm not that rich.").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.GRUM_556)
                }
                2 -> player(FacialExpression.HALF_GUILTY, "No, I', not that rich.").also { stage++ }
            }
            2 -> npc(FacialExpression.ANNOYED, "Get out, then! We don't want any riff-raff in here.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRUM_556)
    }
}
