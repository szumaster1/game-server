package content.region.kandarin.dialogue.seersvillage

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class FionellaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Can I help you at all?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please. What are you selling?", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes please. What are you selling?").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "No thanks.").also { stage = END_DIALOGUE }
            }
            2 -> npc(FacialExpression.FRIENDLY, "Take a look.").also { stage++ }
            3 -> {
                end()
                openNpcShop(player, NPCs.FIONELLA_932)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FIONELLA_932)
    }
}
