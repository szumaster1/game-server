package content.global.skill.combat.summoning.familiar.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Dreadfowl dialogue.
 */
@Initializable
class DreadfowlDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..2).random()) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Attack! Fight! Annihilate!").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_NORMAL, "Can it be fightin' time, please?").also { stage = 1 }
            2 -> npcl(FacialExpression.OLD_NORMAL, "I want to fight something.").also { stage = 2 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "It always worries me when you're so happy saying that.").also { stage = END_DIALOGUE }
            1 -> playerl(FacialExpression.FRIENDLY, "Look I'll find something for you to fight, just give me a second.").also { stage = END_DIALOGUE }
            2 -> playerl(FacialExpression.FRIENDLY, "I'll find something for you in a minute - just be patient.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DREADFOWL_6825, NPCs.DREADFOWL_6826)
    }

}
