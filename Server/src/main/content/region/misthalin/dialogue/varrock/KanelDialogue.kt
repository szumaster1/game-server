package content.region.misthalin.dialogue.varrock

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Kanel dialogue.
 */
@Initializable
class KanelDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: One of the youngest of Gertrude's four children.
     * Location: 3155,3406
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.CHILD_SUSPICIOUS, "Hel-lo?").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "Right. Goodbye.").also { stage++ }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Bye?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KANEL_784)
    }

}
