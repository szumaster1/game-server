package content.region.fremennik.jatizso.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Eric dialogue.
 */
@Initializable
class EricDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HALF_GUILTY, "Spare us a few coppers mister")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        playerl(FacialExpression.ANGRY, "NO!").also { stage = END_DIALOGUE }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ERIC_5499)
    }

}
