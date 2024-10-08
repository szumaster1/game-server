package content.region.misthalin.lumbridge.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Lumbridge Jail guard dialogue.
 */
@Initializable
class LumbridgeJailGuardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Why are you here ? You must leave at once.").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "Err.. Okay.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JAIL_GUARD_917, NPCs.JAIL_GUARD_447, NPCs.JAIL_GUARD_448, NPCs.JAIL_GUARD_449)
    }

}
