package content.global.skill.summoning.familiar.dialogue

import core.api.amountInInventory
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Fruit bat dialogue.
 */
@Initializable
class FruitBatDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (amountInInventory(player!!, Items.PAPAYA_FRUIT_5972) >= 5) {
            npc(FacialExpression.CHILD_NORMAL, "Squeeksqueekasqueeksquee?","(Can I have a papaya?)").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "Squeekasqueek squeek?","(How much longer do you want me for?)").also { stage = 3 }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Squeakdqueesqueak.","(This place is fun!)").also { stage = 4 }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Squeeksqueekasqueek?","(Where are we going?)").also { stage = 5 }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Squeeksqueekasqueek squee?","(Can you smell lemons?)").also { stage = 6 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "No, I have a very specific plan for them.").also { stage++ }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Squeek?","(What?)").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I was just going to grate it over some other vegetables and eat it. Yum.").also { stage = END_DIALOGUE }
            3 -> playerl(FacialExpression.FRIENDLY, "I don't really know at the moment, it all depends what I want to do today.").also { stage = END_DIALOGUE }
            4 -> playerl(FacialExpression.FRIENDLY, "Glad you think so!").also { stage = END_DIALOGUE }
            5 -> playerl(FacialExpression.FRIENDLY, "Oh, we're likely to go to a lot of places today.").also { stage = END_DIALOGUE }
            6 -> npc(FacialExpression.CHILD_NORMAL, "Squeeksqueekasqueek squee?","(Can you smell lemons?)").also { stage++ }
            7 -> playerl(FacialExpression.HALF_ASKING, "No, why do you ask?").also { stage++ }
            8 -> npc(FacialExpression.CHILD_NORMAL, "Squeaksqueak squeaksqueesqueak.","(Must just be thinking about them.)").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FRUIT_BAT_6817)
    }

}
