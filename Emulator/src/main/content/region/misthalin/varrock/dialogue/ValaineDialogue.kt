package content.region.misthalin.varrock.dialogue

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Valaine dialogue.
 */
@Initializable
class ValaineDialogue(player: Player? = null) : Dialogue(player) {

    /*
       Info: Valaine is a merchant found on the 1st floor of the Champions' Guild.
       She runs a shop which sells a selection of ranged armour.
       Location: 3192,3359,1 | Shop ID: 173
    */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello there. Want to have a look at what we're selling", "today?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please.", "No thank you.").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_GUILTY, "Yes please.").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "No thank you.").also { stage = END_DIALOGUE }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.VALAINE_536)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VALAINE_536)
    }

}
