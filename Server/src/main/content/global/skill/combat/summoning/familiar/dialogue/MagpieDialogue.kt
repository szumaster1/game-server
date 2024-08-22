package content.global.skill.combat.summoning.familiar.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Magpie dialogue.
 */
@Initializable
class MagpieDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "There's nowt gannin on here...").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Howway, let's gaan see what's happenin' in toon.").also { stage = 2 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Are we gaan oot soon? I'm up fer a good walk me.").also { stage = 3 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Ye' been plowdin' i' the claarts aall day.").also { stage = 4 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "Err...sure? Maybe?").also { stage++ }
            1 -> playerl(FacialExpression.HALF_ASKING, "It seems upset, but what is it saying?").also { stage = END_DIALOGUE }
            2 -> playerl(FacialExpression.HALF_ASKING, "What? I can't understand what you're saying.").also { stage = END_DIALOGUE }
            3 -> playerl(FacialExpression.HALF_ASKING, "That...that was just noise. What does that mean?").also { stage = END_DIALOGUE }
            4 -> playerl(FacialExpression.HALF_ASKING, "What? That made no sense.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAGPIE_6824)
    }

}
