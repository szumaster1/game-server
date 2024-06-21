package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class BloatedLeechDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "I'm afraid it's going to have to come off, ${player!!.username}.").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "You're in a critical condition.").also { stage = 3 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Let's get a look at that brain of yours.").also { stage = 6 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "I think we're going to need to operate.").also { stage = 9 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0  -> playerl(FacialExpression.HALF_ASKING, "What is?").also { stage++ }
            1  -> npcl(FacialExpression.CHILD_NORMAL, "Never mind. Trust me, I'm almost a doctor.").also { stage++ }
            2  -> playerl(FacialExpression.FRIENDLY, "I think I'll get a second opinion.").also { stage = END_DIALOGUE }

            3  -> playerl(FacialExpression.HALF_ASKING, "Is it terminal?").also { stage++ }
            4  -> npcl(FacialExpression.CHILD_NORMAL, "Not yet. Let me get a better look and I'll see what I can do about it.").also { stage++ }
            5  -> playerl(FacialExpression.FRIENDLY, "There are two ways to take that...and I think I'll err on the side of caution.").also { stage = END_DIALOGUE }

            6  -> playerl(FacialExpression.FRIENDLY, "What? My brains stay inside my head, thanks.").also { stage++ }
            7  -> npcl(FacialExpression.CHILD_NORMAL, "That's ok, I can just drill a hole.").also { stage++ }
            8  -> playerl(FacialExpression.HALF_ASKING, "How about you don't and pretend you did?").also { stage = END_DIALOGUE }

            9  -> playerl(FacialExpression.FRIENDLY, "I think we can skip that for now.").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "Who's the doctor here?").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Not you.").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "I may not be a doctor, but I'm keen. Does that not count?").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "In most other fields, yes; in medicine, no.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLOATED_LEECH_6843, NPCs.BLOATED_LEECH_6844)
    }

}
