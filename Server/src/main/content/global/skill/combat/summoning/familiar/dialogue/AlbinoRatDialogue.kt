package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Albino rat dialogue.
 */
@Initializable
class AlbinoRatDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Hey boss, we going to do anything wicked today?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Hey boss, can we go and loot something now?").also { stage = 4 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "So what we up to today, boss?").also { stage = 9 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "You know, boss, I don't think you're totally into this whole 'evil' thing.").also { stage = 13 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Well, I don't know why we would: I tend not to go around being wicked.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Not even a little?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Well there was that one time... I'm sorry, no wickedness today.").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Awwwwww...").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.HALF_ASKING, "Well, what did you have in mind?").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "I dunno - where are we headed?").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "I hadn't decided yet.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "When we get there, let's loot something nearby!").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Sounds like a plan, certainly.").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.FRIENDLY, "Oh I'm sure we'll find something to occupy our time.").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "Let's go robbin' graves again!").also { stage++ }
            11 -> playerl(FacialExpression.ASKING, "What do you mean 'again'?").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "Nuffin'...").also { stage = END_DIALOGUE }

            13 -> playerl(FacialExpression.HALF_ASKING, "I wonder what gave you that impression?").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_NORMAL, "Well, I worked with a lot of evil people; some of the best.").also { stage++ }
            15 -> playerl(FacialExpression.HALF_ASKING, "Such as?").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_NORMAL, "I'm not telling! I've got my principles to uphold.").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "There is honour amongst thieves, it would seem.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALBINO_RAT_6847, NPCs.ALBINO_RAT_6848)
    }

}
