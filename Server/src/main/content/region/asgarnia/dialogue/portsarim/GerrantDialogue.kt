package content.region.asgarnia.dialogue.portsarim

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Gerrant dialogue.
 */
@Initializable
class GerrantDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Gerrant is the owner of Gerrant's Fishy Business in Port Sarim.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Welcome! You can buy fishing equipment at my store.", "We'll also buy anything you catch off you.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Let's see what you've got then.", "Sorry, I'm not interested.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Let's see what you've got then.").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "Sorry, I'm not interested.").also { stage = END_DIALOGUE }
            }
            10 -> {
                end()
                openNpcShop(player, NPCs.GERRANT_558)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GERRANT_558)
    }
}
