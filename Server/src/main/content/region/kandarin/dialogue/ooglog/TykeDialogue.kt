package content.region.kandarin.dialogue.ooglog

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Tyke dialogue.
 */
@Initializable
class TykeDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.CHILD_NORMAL, "Hi, human.").also { stage++ }
            1 -> playerl(FacialExpression.HALF_ASKING, "Hi, ogre! How are you today, little ogre?").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Hey, human. What did you bring me?").also { stage++ }
            3 -> playerl(FacialExpression.THINKING, "Hmm, let me think carefully about this. Oh, yes. I remember, now! Absolutely nothing.").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Aw, shucks.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TYKE_7068)
    }
}
