package content.region.misc.keldagrim.dialogue

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Myndill dialogue.
 */
@Initializable
class MyndillDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Hello there, human.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Do you have any news?", "Do you have any quests?", "See you later!").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_ASKING, "Do you have any news?").also { stage = 4 }
                2 -> player(FacialExpression.HALF_ASKING, "Do you have any quests?").also { stage = 10 }
                3 -> player(FacialExpression.FRIENDLY, "See you later!").also { stage = 12 }
            }
            4 -> npc(FacialExpression.OLD_NORMAL, "The Red Axe has departed the city recently.", "Ill omens.").also { stage++ }
            5 -> player(FacialExpression.HALF_ASKING, "How is that? Do you know anything", "about this?").also { stage++ }
            6 -> npc(FacialExpression.OLD_NORMAL, "No, it's just that no company has ever willingly", "left the Consortium before.").also { stage++ }
            7 -> player(FacialExpression.HALF_ASKING, "Companies have been forced out of the", "Consortium then?").also { stage++ }
            8 -> npc(FacialExpression.OLD_NORMAL, "Oh yes. It hasn't happened for a long while now,", "but sometimes a company goes bankrupt. Or the value", "of the company becomes so low that it's relegated", "to being a minor company.").also { stage++ }
            9 -> player(FacialExpression.FRIENDLY, "I see, thank you.").also { stage = END_DIALOGUE }
            10 -> npc(FacialExpression.OLD_NORMAL, "Let me think for a moment...").also { stage++ }
            11 -> npc(FacialExpression.OLD_NORMAL, "No, I have nothing that I could possible", "want doing. Try the shops or the market instead.").also { stage = 0 }
            12 -> npc(FacialExpression.OLD_NORMAL, "Perhaps!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MYNDILL_2197)
    }
}
