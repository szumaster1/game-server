package content.region.fremennik.dialogue.miscellania

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Finn dialogue.
 */
@Initializable
class FinnDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Can I help you, your Royal Highness?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please. What are you selling?", "No thanks.", "What's it like living down here?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Yes please. What are you selling?").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "No thanks.").also { stage = END_DIALOGUE }
                3 -> player(FacialExpression.ASKING, "What's it like living down here?").also { stage = 3 }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.FINN_3922)
            }
            3 -> npc(FacialExpression.HALF_WORRIED, "A lot drier in the winter than it is above ground.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FINN_3922)
    }
}
