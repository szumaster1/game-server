package content.region.karamja.shilovillage.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Fernahei dialogue.
 */
@Initializable
class FernaheiDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Welcome to Fernahei's Fishing Shop Bwana!", "Would you like to see my items?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please!", "No, but thanks for the offer.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes, please.").also { stage = 10 }
                2 -> player(FacialExpression.FRIENDLY, "No, but thanks for the offer.").also { stage = 20 }
            }
            10 -> {
                end()
                openNpcShop(player, NPCs.FERNAHEI_517)
            }
            20 -> npc(FacialExpression.FRIENDLY, "That's fine, and thanks for your interest.").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FERNAHEI_517)
    }
}
