package content.dialogue.falador

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Falador gardener dialogue.
 */
@Initializable
class FaladorGardenerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Oi'm busy. If tha' wants owt, tha' can go find Wyson.", "He's ta boss 'round here. And,", "KEEP YE' TRAMPIN' FEET OFF MA'FLOWERS!").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "Right...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GARDENER_1217, NPCs.GARDENER_3234)
    }

}
