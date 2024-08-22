package content.region.kandarin.dialogue.ooglog

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Grrbah dialogue.
 */
@Initializable
class GrrbahDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.CHILD_NORMAL, "Hi, human.").also { stage++ }
            1 -> playerl(FacialExpression.HALF_ASKING, "Hi, ogre! How are you today, little ogre?").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "How does it feel to be so puny wee small, human?").also { stage++ }
            3 -> playerl(FacialExpression.THINKING, "Oh, I dunno. How does it feel to be so incredibly dense?").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Uhh... what dat s'pposed to mean?").also { stage++ }
            5 -> playerl(FacialExpression.NEUTRAL, "Never mind.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRRBAH_7073)
    }
}
