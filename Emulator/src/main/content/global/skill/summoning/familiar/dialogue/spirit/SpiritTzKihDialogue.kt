package content.global.skill.summoning.familiar.dialogue.spirit

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Spirit tz kih dialogue.
 */
@Initializable
class SpiritTzKihDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> playerl(FacialExpression.FRIENDLY, "How's it going, Tz-kih?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Does JalYt think Tz-kih as strong as Jad Jad?").also { stage = 3 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Have you heard of blood bat, JalYt?").also { stage = 5 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Pray pray pray pray pray pray pray pray!").also { stage = 10 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "You drink pray, me drink pray.").also { stage = 13 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Pray pray?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Don't start with all that again.").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Hmph, silly JalYt.").also { stage = END_DIALOGUE }

            3 -> playerl(FacialExpression.FRIENDLY, "Are you as strong as TzTok-Jad? Yeah, sure, why not.").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Really? Thanks, JalYt. Tz-Kih strong and happy.").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "Blood bats? You mean vampire bats?").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Yes. Blood bat.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Yes, I've heard of them. What about them?").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "Tz-Kih like blood bat, but drink pray pray not blood blood. Blood blood is yuck.").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Thanks, Tz-Kih, that's nice to know.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "FRIENDLY down, Tz-Kih, we'll find you something to drink soon.").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "Pray praaaaaaaaaaaaaay!").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Okay, okay. FRIENDLY down!").also { stage = END_DIALOGUE }

            13 -> playerl(FacialExpression.FRIENDLY, "What's that, Tz-Kih?").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_NORMAL, "You got pray pray pot. Tz-Kih drink pray pray you, you drink pray pray pot.").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "You want to drink my Prayer points?").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_NORMAL, "Yes. Pray pray.").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "Err, not right now, Tz-Kih. I, er, need them myself.").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Sorry.").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_NORMAL, "But, pray praaaay...?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_TZ_KIH_7361, NPCs.SPIRIT_TZ_KIH_7362)
    }

}
