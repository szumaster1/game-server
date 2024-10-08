package content.region.fremennik.quest.misc.dialogue

import org.rs.consts.NPCs
import content.region.fremennik.diary.dialogue.AdvisorGhrimDiaryDialogue
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Advisor Ghrim dialogue.
 */
@Initializable
class AdvisorGhrimDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npcl(FacialExpression.HALF_GUILTY, "Greetings, ${if (player.isMale) "Sir" else "Madam"}").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("How do I make peace with Etceteria?", "About the Achievement Diary...").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.ANGRY, "How do I make peace with Etceteria?").also { stage++ }
                2 -> openDialogue(player, AdvisorGhrimDiaryDialogue()).also { stage = 100 }
            }
            2 -> npcl(FacialExpression.HALF_GUILTY, "You should go talk to Queen Sigrid of Etceteria.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ADVISOR_GHRIM_1375)
    }
}
