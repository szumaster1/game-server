package content.region.misthalin.lumbridge.dialogue.swamp

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Lumbridge swamp Monk dialogue.
 */
@Initializable
class LumbridgeSwampMonkDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Why are all of you standing around here?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "None of your business. Get lost.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return LumbridgeSwampMonkDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MONK_651)
    }
}
