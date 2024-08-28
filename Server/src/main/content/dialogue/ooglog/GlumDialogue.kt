package content.dialogue.ooglog

import cfg.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Glum dialogue.
 */
@Initializable
class GlumDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> {
                if (isQuestComplete(player, "As a First Resort")) {
                    npcl(FacialExpression.CHILD_NORMAL, "Do you know anything about this ship?").also { stage++ }
                } else {
                    npcl(FacialExpression.CHILD_NORMAL, "RAAAAAAAGH!").also { stage = 2 }
                }
            }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "No, I just waiting here for de humans who make it go to come back. I wanna ride when dey open for business.").also { stage = END_DIALOGUE }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Stupid humans. Dey won't let me have a ride in de boat unless I gives dem lots of shiny pretties.").also { stage++ }
            3 -> playerl(FacialExpression.NEUTRAL, "That's so often the way, I'm afraid.").also { stage++ }
            4 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GLUM_7077)
    }
}
