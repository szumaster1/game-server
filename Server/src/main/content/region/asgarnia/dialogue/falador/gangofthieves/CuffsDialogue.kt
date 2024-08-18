package content.region.asgarnia.dialogue.falador.gangofthieves

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Cuffs dialogue.
 */
@Initializable
class CuffsDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello. nice day for a walk, isn't it?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "A walk? Oh, yes, that's what we're doing.", "We're just out here for a walk.").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "I'm glad you're just out here for a walk. A more suspicious", "person would think you were waiting here to attack weak-", "looking travellers.").also { stage++ }
            2 -> npc(FacialExpression.HALF_GUILTY, "Nope, we'd never do anything like that.", "Just a band of innocent walkers, that's us.").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "Alright, have a nice walk.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CUFFS_3237)
    }

}