package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Giant ent dialogue.
 */
@Initializable
class GiantEntDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..7).random()) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "Creeeeeeeeeeeak.....","(I.....)").also { stage = 0 }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Creak..... Creaaaaaaaaak.....","(Am.....)").also { stage = 0 }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Grooooooooan.....","(Feeling.....)").also { stage = 3 }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Groooooooooan.....","(Sleepy.....)").also { stage = 4 }
            4 -> npc(FacialExpression.CHILD_NORMAL, "Grooooooan.....creeeeeeeak","(Restful.....)").also { stage = 4 }
            5 -> npc(FacialExpression.CHILD_NORMAL, "Grrrrooooooooooooooan.....","(Achey.....)").also { stage = 4 }
            6 -> npc(FacialExpression.CHILD_NORMAL, "Creeeeeeeegroooooooan.....","(Goood.....)").also { stage = 4 }
            7 -> npc(FacialExpression.CHILD_NORMAL, "Creeeeeeeeeeeeeaaaaaak.....","(Tired.....)").also { stage = 4 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.ASKING, "Yes?").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, ".....").also { stage++ }
            2 -> sendDialogue(player!!, "After a while you realise that the ent has finished speaking for the moment.").also { stage = END_DIALOGUE }
            3 -> playerl(FacialExpression.ASKING, "Yes? We almost have a full sentence now - the suspense is killing me!").also { stage = 1 }
            4 -> playerl(FacialExpression.ASKING, "I'm not sure if that was worth all the waiting.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GIANT_ENT_6800, NPCs.GIANT_ENT_6801)
    }

}
