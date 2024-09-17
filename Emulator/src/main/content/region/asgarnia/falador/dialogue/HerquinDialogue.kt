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
 * Represents the Herquin dialogue.
 */
@Initializable
class HerquinDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        options("Do you wish to trade?", "Sorry, I don't want to talk to you, actually.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.FRIENDLY, "Do you wish to trade?").also { stage = 3 }
            1 -> player(FacialExpression.HALF_GUILTY, "Sorry, I don't want to talk to you, actually.").also { stage++ }
            2 -> npc(FacialExpression.ROLLING_EYES, "Huh, charming.").also { stage = END_DIALOGUE }
            3 -> npc(FacialExpression.FRIENDLY, "Why, yes, this is a jewel shop after all.").also { stage++ }
            4 -> {
                end()
                openNpcShop(player, NPCs.HERQUIN_584)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HERQUIN_584)
    }
}
