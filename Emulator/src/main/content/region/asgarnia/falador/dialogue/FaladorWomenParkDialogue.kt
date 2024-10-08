package content.region.asgarnia.falador.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Falador women park dialogue.
 */
@Initializable
class FaladorWomenParkDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Greetings! Have you come to gaze in rapture at the", "natural beauty of Falador's parkland?").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "Um, yes, very nice. Lots of.... trees and stuff.").also { stage++ }
            2 -> npc(FacialExpression.HALF_GUILTY, "Trees! I do so love trees! And flowers! And squirrels.").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "Sorry, I have a strange urge to be somewhere else.").also { stage++ }
            4 -> npc(FacialExpression.HALF_GUILTY, "Come back to me soon and we can talk again about trees!").also { stage++ }
            5 -> player(FacialExpression.HALF_GUILTY, "...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WOMAN_3226)
    }

}
