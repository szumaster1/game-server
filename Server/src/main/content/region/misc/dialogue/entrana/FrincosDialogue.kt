package content.region.misc.dialogue.entrana

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Frincos dialogue.
 */
@Initializable
class FrincosDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello, how can I help you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What are you selling?", "You can't; I'm beyond help.", "I'm okay, thank you.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "What are you selling?").also { stage++ }
                2 -> player(FacialExpression.HALF_GUILTY, "You can't; I'm beyond help.").also { stage = END_DIALOGUE }
                3 -> player(FacialExpression.HALF_GUILTY, "I'm okay, thank you.").also { stage = END_DIALOGUE }

            }
            2 -> {
                end()
                openNpcShop(player, npc.id)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FRINCOS_578)
    }
}
