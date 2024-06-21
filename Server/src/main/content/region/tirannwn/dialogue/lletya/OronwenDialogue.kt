package content.region.tirannwn.dialogue.lletya

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class OronwenDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Hello, can I help?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please. What are you selling?", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes please. What are you selling?").also { stage = 10 }
                2 -> player(FacialExpression.FRIENDLY, "No thanks.").also { stage = END_DIALOGUE }
            }
            10 -> {
                end()
                openNpcShop(player, NPCs.ORONWEN_2353)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ORONWEN_2353)
    }
}
