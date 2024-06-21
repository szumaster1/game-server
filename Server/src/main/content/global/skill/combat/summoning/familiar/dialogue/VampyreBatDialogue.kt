package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.api.inBorders
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class VampyreBatDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inBorders(player!!, 3139, 9535, 3306, 9657)) {
            npcl(FacialExpression.CHILD_NORMAL, "Ze creatures ov ze dark; vat vonderful music zey make.").also { stage = 0 }
            return true
        }
        when ((0..2).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "You're vasting all that blood, can I have some?").also { stage = 3 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Ven are you going to feed me?").also { stage = 4 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Ven can I eat somethink?").also { stage = 5 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Riiight.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "I like it down here. Let's stay and eat moths!").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I think I'll pass, thanks.").also { stage = END_DIALOGUE }
            3 -> playerl(FacialExpression.FRIENDLY, "No!").also { stage = END_DIALOGUE }
            4 -> playerl(FacialExpression.FRIENDLY, "Well for a start, I'm not giving you any of my blood.").also { stage = END_DIALOGUE }
            5 -> playerl(FacialExpression.FRIENDLY, "Just as soon as I find something to attack.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VAMPIRE_BAT_6835, NPCs.VAMPIRE_BAT_6836)
    }

}
