package content.region.asgarnia.falador.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Sarah farming dialogue.
 */
@Initializable
class SarahFarmingDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "Hi!").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "Would you like to see what I have in stock?").also { stage++ }
            2 -> options("Yes please.", "No, thank you.").also { stage++ }
            3 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Yes please.").also { stage++ }
                2 -> player(FacialExpression.HALF_GUILTY, "No, thank you.").also { stage = END_DIALOGUE }
            }
            4 -> {
                end()
                openNpcShop(player, NPCs.SARAH_2304)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SARAH_2304)
    }
}
