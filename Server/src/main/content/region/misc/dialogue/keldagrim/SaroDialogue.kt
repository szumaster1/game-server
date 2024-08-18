package content.region.misc.dialogue.keldagrim

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Saro dialogue.
 */
@Initializable
class SaroDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Welcome to my store, human! Are you interested", "in buying anything?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, I'm looking for some armour.", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes, I'm looking for some armour.").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "No thanks.").also { stage = END_DIALOGUE }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.SARO_2153)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SARO_2153)
    }

}
