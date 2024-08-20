package content.global.skill.combat.summoning.familiar.dialogue.spirit

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Spirit scorpion dialogue.
 */
@Initializable
class SpiritScorpionDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Hey, boss, how about we go to the bank?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Say hello to my little friend!").also { stage = 9 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Hey, boss, I've been thinking.").also { stage = 13 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Why do we never go to crossroads and rob travelers?").also { stage = 20 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "And do what?").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Well, we could open by shouting, 'Stand and deliver!'").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Why does everything with you end with something getting held up?").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "That isn't true! Give me one example.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "How about the post office?").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "How about another?").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Those junior White Knights? The ones selling the gnome crunchies?").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "That was self defence.").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "No! No more hold-ups, stick-ups, thefts, or heists, you got that?").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.ASKING, "What?").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "My little friend: you ignored him last time you met him.").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "So, who is your friend?").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "If I tell you, what is the point?").also { stage = END_DIALOGUE }

            13 -> playerl(FacialExpression.FRIENDLY, "That's never a good sign.").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_NORMAL, "See, I heard about this railway...").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "We are not robbing it!").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_NORMAL, "I might not have wanted to suggest that, boss...").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "Then what were you going to suggest?").also { stage++ }
            18 -> npcl(FacialExpression.CHILD_NORMAL, "That isn't important right now.").also { stage++ }
            19 -> playerl(FacialExpression.FRIENDLY, "I thought as much.").also { stage = END_DIALOGUE }

            20 -> playerl(FacialExpression.FRIENDLY, "There are already highwaymen at the good spots.").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_NORMAL, "Maybe we need to think bigger.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_SCORPION_6837, NPCs.SPIRIT_SCORPION_6838)
    }

}
