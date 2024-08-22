package content.region.kandarin.dialogue.stronghold

import cfg.consts.NPCs
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Dalila and perrdur dialogue.
 */
@Initializable
class DalilaAndPerrdurDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.OLD_NORMAL, "Ooh, lovely view, dear.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendNPCDialogue(player, NPCs.PERRDUR_4587, "Yes dear.", FacialExpression.OLD_NORMAL).also { stage++ }
            1 -> npcl(FacialExpression.OLD_NORMAL, "I hope the food's better here than it was last time.").also { stage++ }
            2 -> sendNPCDialogue(player, NPCs.PERRDUR_4587, "I'm sure it will be, dear.", FacialExpression.OLD_NORMAL).also { stage++ }
            3 -> playerl(FacialExpression.HALF_ASKING, "Sorry to disturb you, but what are you two doing here?").also { stage++ }
            4 -> npcl(FacialExpression.OLD_NORMAL, "Perrdur and I are having a nice romantic dinner, aren't we dear?").also { stage++ }
            5 -> sendNPCDialogue(player, NPCs.PERRDUR_4587, "Yes dear.", FacialExpression.OLD_NORMAL).also { stage++ }
            6 -> npcl(FacialExpression.OLD_NORMAL, "We are celebrating the anniversary of our marriage, aren't we dear?").also { stage++ }
            7 -> sendNPCDialogue(player, NPCs.PERRDUR_4587, "Yes dear.", FacialExpression.OLD_NORMAL).also { stage++ }
            8 -> npcl(FacialExpression.OLD_NORMAL, "And we are having a very enjoyable time, aren't we dear?").also { stage++ }
            9 -> sendNPCDialogue(player, NPCs.PERRDUR_4587, "Yes dear.", FacialExpression.OLD_NORMAL).also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Clearly. I'll let you two lovebirds carry on, then.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DALILA_4588, NPCs.PERRDUR_4587)
    }
}
