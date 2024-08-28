package content.dialogue.ooglog

import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Thump dialogue.
 */
@Initializable
class ThumpDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> if (isQuestComplete(player, "As a First Resort")) {
                npcl(FacialExpression.CHILD_NORMAL, "C'mere, human. Me need practice at dis massage thing. Me not sure how to do it without breaking spine of small, puny creatures.").also { stage++ }
            } else {
                npcl(FacialExpression.CHILD_NORMAL, "RAAAAAAAGH!").also { stage = 2 }
            }
            1 -> playerl(FacialExpression.FRIENDLY, "I...think I'll take a rain check on that.").also { stage = END_DIALOGUE }
            2 -> sendNPCDialogue(player, NPCs.GNOME_66, "Send...help...!", FacialExpression.OLD_ALMOST_CRYING).also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray = intArrayOf(7101) // NPCs.THUMP_7059, NPCs.THUMP_7061

}
