package content.global.skill.combat.summoning.familiar.dialogue

import content.global.skill.support.firemaking.Log
import core.api.anyInInventory
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class BeaverDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if(anyInInventory(player, *logs)){
            npcl(FacialExpression.CHILD_NORMAL, "'Ere, you 'ave ze logs, now form zem into a mighty dam!").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Vot are you doing 'ere when we could be logging and building mighty dams, alors?").also { stage = 2 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Pardonnez-moi - you call yourself a lumberjack?").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Paul Bunyan 'as nothing on moi!").also { stage = 7 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Zis is a fine day make some lumber.").also { stage = 10 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Well, I was thinking of burning, selling, or fletching them.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Sacre bleu! Such a waste.").also { stage = END_DIALOGUE }

            2 -> playerl(FacialExpression.HALF_ASKING, "Why would I want to build a dam again?").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Why wouldn't you want to build a dam again?").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "I can't argue with that logic.").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "No").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Carry on zen.").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.FRIENDLY, "Except several feet in height, a better beard, and opposable thumbs.").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "What was zat?").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Nothing.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "That it is!").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "So why are you talking to moi? Get chopping!").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BEAVER_6808)
    }

    companion object {
        private val logs = Log.values().map { it.logId }.toIntArray()
    }

}
