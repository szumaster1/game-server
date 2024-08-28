package content.dialogue.keldagrim

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Runvastr dialogue.
 */
@Initializable
class RunvastrDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_DEFAULT, "Oh, leave an old dwarf in peace will you?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Why, what's wrong?").also { stage++ }
            2 -> npcl(FacialExpression.OLD_DEFAULT, "Ninety years down in the mines, and this is all I got out of it. All my life wasted away.").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "You've still got a nice house, don't you?").also { stage++ }
            4 -> npcl(FacialExpression.OLD_NORMAL, "A crummy old house in East, that's all my life amounts to.").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "I'll leave you alone then.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RUNVASTR_2190)
    }
}
