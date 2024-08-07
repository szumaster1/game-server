package content.global.skill.combat.summoning.familiar.dialogue.void

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Void torcher dialogue.
 */
@Initializable
class VoidTorcherDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> playerl(FacialExpression.HALF_ASKING, "You okay there, spinner?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "'T' is for torcher, that's good enough for me... 'T' is for torcher, I'm happy you can see.").also { stage = 7 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Burn, baby, burn! Torcher inferno!").also { stage = 8 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "So hungry... must devour...").also { stage = 9 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "I not spinner!").also { stage++ }
            1 -> playerl(FacialExpression.HALF_ASKING, "Sorry, splatter?").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "I not splatter either!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "No, wait, I meant defiler.").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "I torcher!").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "Hehe, I know. I was just messing with you.").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Grr. Don't be such a pest.").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.HALF_ASKING, "You're just a bit weird, aren't you?").also { stage = END_DIALOGUE }

            8 -> playerl(FacialExpression.FRIENDLY, "*Wibble*").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.FRIENDLY, "*Gulp* Er, yeah, I'll find you something to eat in a minute.").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "Is flesh-bag scared of torcher?").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "No, no. I, er, always look like this... honest.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VOID_TORCHER_7351, NPCs.VOID_TORCHER_7352)
    }

}
