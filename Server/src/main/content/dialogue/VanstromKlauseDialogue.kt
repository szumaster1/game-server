package content.dialogue

import content.region.morytania.quest.route.dialogue.VanstromKlauseQuestDialogue
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Vanstrom Klause dialogue.
 */
@Initializable
class VanstromKlauseDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello there, how goes it stranger?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_GUILTY, "Quite well thanks for asking, how about you?").also { stage++ }
            1 -> npcl(FacialExpression.HALF_GUILTY, "Quite well my self.").also { stage++ }
            2 -> {
                when {
                    getQuestStage(player, "In Search of the Myreque") > 1 -> openDialogue(player, VanstromKlauseQuestDialogue(), npc)
                    else -> end()
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(2020)
    }

}