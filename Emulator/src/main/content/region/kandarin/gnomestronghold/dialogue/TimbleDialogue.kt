package content.region.kandarin.gnomestronghold.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Timble dialogue.
 */
@Initializable
class TimbleDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.OLD_NORMAL, "Good day. How can I help you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "I was just wondering what you were up to.").also { stage++ }
            1 -> npcl(FacialExpression.OLD_NORMAL, "I work for Aluft Aloft Gnome Food Delivery Service. It's a growing business you know. We get more customers every day!").also { stage++ }
            2 -> playerl(FacialExpression.HALF_ASKING, "So do you have any spare jobs then?").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Aluft Gianne jnr. is in charge - he'll be able to help you.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Thanks.").also { stage++ }
            5 -> npcl(FacialExpression.OLD_NORMAL, "Have a nice day.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TIMBLE_4573)
    }
}
