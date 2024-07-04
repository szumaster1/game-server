package content.region.fremennik.dialogue.miscellania

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class DerrikDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Good day, Sir. Can I help you with anything?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Can I use your anvil?", "Nothing, thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Can I use your anvil?").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "Nothing, thanks.").also { stage = END_DIALOGUE }
            }
            2 -> npc(FacialExpression.NEUTRAL, "You may.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DERRIK_1376)
    }
}
