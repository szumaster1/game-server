package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Smoke devil dialogue.
 */
@Initializable
class SmokeDevilDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "When are you going to be done with that?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Hey!").also { stage = 6 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Ah, this is the life!").also { stage = 16 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Why is it always so cold here?").also { stage = 22 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Soon, I hope.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Good, because this place is too breezy.").also { stage++ }
            2 -> playerl(FacialExpression.HALF_ASKING, "What do you mean?").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "I mean, it's tricky to keep hovering in this draft.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Ok, we'll move around a little if you like.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "Yes please!").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.HALF_ASKING, "Yes?").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "Where are we going again?").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Well, I have a lot of things to do today, so we might go a lot of places.").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "Are we there yet?").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "No, not yet.").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "How about now?").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "No.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "Are we still not there?").also { stage++ }
            14 -> playerl(FacialExpression.ANNOYED, "NO!").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "Okay, just checking.").also { stage = END_DIALOGUE }

            16 -> playerl(FacialExpression.HALF_ASKING, "Having a good time up there?").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah! It's great to feel the wind in your tentacles.").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Sadly, I don't know what that feels like.").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_NORMAL, "Why not?").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "No tentacles for a start.").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_NORMAL, "Well, nobody's perfect.").also { stage = END_DIALOGUE }

            22 -> playerl(FacialExpression.FRIENDLY, "I don't think it's that cold.").also { stage++ }
            23 -> npcl(FacialExpression.CHILD_NORMAL, "It is compared to back home.").also { stage++ }
            24 -> playerl(FacialExpression.FRIENDLY, "How hot is it where you are from?").also { stage++ }
            25 -> npcl(FacialExpression.CHILD_NORMAL, "I can never remember. What is the vaporisation point of steel again?").also { stage++ }
            26 -> playerl(FacialExpression.FRIENDLY, "Pretty high.").also { stage++ }
            27 -> playerl(FacialExpression.FRIENDLY, "No wonder you feel cold here...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SMOKE_DEVIL_6865, NPCs.SMOKE_DEVIL_6866)
    }

}
