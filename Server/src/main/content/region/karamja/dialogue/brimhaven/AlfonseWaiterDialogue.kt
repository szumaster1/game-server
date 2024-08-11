package content.region.karamja.dialogue.brimhaven

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Alfonse Waiter dialogue.
 */
@Initializable
class AlfonseWaiterDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Welcome to the Shrimp and Parrot.", "Would you like to order, sir?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please.", "No, thank you.", "Where do you get your Karambwan from?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Yes, please.").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "No, thank you.").also { stage = END_DIALOGUE }
                3 -> player(FacialExpression.HALF_GUILTY, "Where do you get your Karambwan from?").also { stage = 30 }
            }
            10 -> {
                end()
                openNpcShop(player, NPCs.ALFONSE_THE_WAITER_793)
            }
            30 -> npc(FacialExpression.HALF_GUILTY, "We buy directly off Lubufu, a local fisherman. He", "seems to have a monopoly over Karambwan sales.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALFONSE_THE_WAITER_793)
    }
}
