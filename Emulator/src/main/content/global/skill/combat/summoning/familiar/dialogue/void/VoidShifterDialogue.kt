package content.global.skill.combat.summoning.familiar.dialogue.void

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Void shifter dialogue.
 */
@Initializable
class VoidShifterDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "What a splendid day, " + (if (player!!.isMale) "sir" else "madam") + "!").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "I'm sorry to bother you, but could you assist me briefly?").also { stage = 3 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "How do you do?").also { stage = 9 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Lets go and see to those cads and bounders!").also { stage = 11 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Yes, it is!").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "It could only be marginally improved, perhaps, by tea and biscuits.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "What a marvellous idea!").also { stage = END_DIALOGUE }

            3 -> playerl(FacialExpression.FRIENDLY, "I suppose so.").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "I was wondering, briefly, if perchance you might care to dance?").also { stage++ }
            5 -> playerl(FacialExpression.HALF_ASKING, "Dance? With a pest?").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Well, you see, I'm dreadfully out of practice and now I can barely leap, let alone teleport.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "I'm not going to help you remember how to destroy the world!").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "What a beastly world we live in where one " + if (player!!.isMale) "gentleman" else "lady" + " will not aid a pest in need...").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.FRIENDLY, "Okay, I suppose.").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "Marvellous, simply marvellous!").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.HALF_ASKING, "Which 'cads and bounders' did you mean, exactly?").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "Why, the ones with no honour, of course.").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "I don't think he knows what pests do...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VOID_SHIFTER_7367, NPCs.VOID_SHIFTER_7368, NPCs.VOID_SHIFTER_7369)
    }

}
