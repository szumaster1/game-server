package content.dialogue.taverley

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Jatix dialogue.
 */
@Initializable
class JatixDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Jatix is the herblore skill tutor and the owner
     * of Jatix's Herblore Shop, a Herblore shop
     * in eastern Taverley.
     */

    override fun newInstance(player: Player): Dialogue {
        return JatixDialogue(player)
    }

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Hello, adventurer.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
            1 -> player(FacialExpression.FRIENDLY, "What are you selling?").also { stage++ }
            2 -> {
                end()
                openNpcShop(player, NPCs.JATIX_587)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JATIX_587)
    }

}
