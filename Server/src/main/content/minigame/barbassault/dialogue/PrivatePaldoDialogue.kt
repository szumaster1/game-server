package content.minigame.barbassault.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Private Paldo dialogue.
 * @author Szumaster
 */
@Initializable
class PrivatePaldoDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hi, soldier.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.HALF_GUILTY, "Why hello there! Have you heard about Primalmoose?").also { stage++ }
            1 -> playerl(FacialExpression.HALF_GUILTY, "What's a primal moose?").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "A barbarian! Word is he's been smashing through waves of Penance faster than anyone we've ever seen.").also { stage++ }
            3 -> npcl(FacialExpression.HALF_GUILTY, "That's why we've decided to battle harder set of waves to challenge the veterans.").also { stage++ }
            4 -> playerl(FacialExpression.HALF_GUILTY, "Well, I suppose that's a good idea.").also { stage++ }
            5 -> npcl(FacialExpression.HALF_GUILTY, "You should try them yourself. Put your skills to the test against the Penance King!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PRIVATE_PALDO_5031)
    }

}
