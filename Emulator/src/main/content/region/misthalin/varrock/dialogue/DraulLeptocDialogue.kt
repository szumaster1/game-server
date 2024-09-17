package content.region.misthalin.varrock.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Draul leptoc dialogue.
 */
@Initializable
class DraulLeptocDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Walthy nobleman who can be found in his mansion in Varrock. He is Juliet's father.
     * Location: 3159,3433
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "What are you doing in my house..why the", "impertinence...the sheer cheek...how dare you violate my", "personal lodgings....")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "I...I was just looking around...").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "Well get out! Get out....this is my house....and don't go", "near my daughter Juliet...she's grounded in her room", "to keep her away from that good for nothing Romeo.").also { stage++ }
            2 -> player(FacialExpression.HALF_GUILTY, "Yes....sir....").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return DraulLeptocDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DRAUL_LEPTOC_3324)
    }

}
