package content.region.karamja.dialogue.shilovillage

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class ObliDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Welcome to Obli's General Store Bwana!", "Would you like to see my items?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please!", "No, but thanks for the offer.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes, please.").also { stage = 10 }
                2 -> player(FacialExpression.FRIENDLY, "No, but thanks for the offer.").also { stage = END_DIALOGUE }
            }
            10 -> {
                end()
                openNpcShop(player, NPCs.OBLI_516)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OBLI_516)
    }
}
