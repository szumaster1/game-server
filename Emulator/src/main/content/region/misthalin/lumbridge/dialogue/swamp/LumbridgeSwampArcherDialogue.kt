package content.region.misthalin.lumbridge.dialogue.swamp

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Lumbridge swamp Archer dialogue.
 */
@Initializable
class LumbridgeSwampArcherDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Why are you guys hanging around here?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "(ahem)...'Guys'?").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "Uh... yeah, sorry about that. Why are you all standing", "around out here?").also { stage++ }
            2 -> npc(FacialExpression.HALF_GUILTY, "Well, that's really none of your business.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return LumbridgeSwampArcherDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ARCHER_649)
    }
}
