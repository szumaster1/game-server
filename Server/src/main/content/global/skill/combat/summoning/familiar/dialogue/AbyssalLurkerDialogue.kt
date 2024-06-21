package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class AbyssalLurkerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Djrej gf'ig sgshe...").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "To poshi v'kaa!").also { stage = 1 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "G-harrve shelmie?").also { stage = 2 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Jehifk i'ekfh skjd.").also { stage = 3 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "What? Are we in danger, or something?").also { stage = END_DIALOGUE }
            1 -> playerl(FacialExpression.HALF_ASKING, "What? Is that even a language?").also { stage = END_DIALOGUE }
            2 -> playerl(FacialExpression.HALF_ASKING, "What? Do you want something?").also { stage = END_DIALOGUE }
            3 -> playerl(FacialExpression.HALF_ASKING, "What? Is there somebody down an old well, or something?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ABYSSAL_LURKER_6820, NPCs.ABYSSAL_LURKER_6821)
    }

}
