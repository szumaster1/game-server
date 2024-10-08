package content.region.kandarin.quest.grandtree.dialogue

import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Shipyard worker dialogue.
 */
@Initializable
class ShipyardWorkerDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (stage) {
            0 -> if (getQuestStage(player, QuestName.THE_GRAND_TREE) == 55) {
                openDialogue(player, ShipyardWorkerGTDialogue(), NPC(NPCs.SHIPYARD_WORKER_675))
            } else {
                playerl(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
            }

            1 -> npcl(FacialExpression.FRIENDLY, "Hello matey!").also { stage++ }
            2 -> playerl(FacialExpression.ASKING, "How are you?").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "Tired!").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "You shouldn't work so hard!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SHIPYARD_WORKER_675, NPCs.SHIPYARD_WORKER_38, NPCs.SHIPYARD_WORKER_39)
    }
}

