package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.toIntArray
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE
import core.utilities.START_DIALOGUE

@Initializable
class BreocaDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (isQuestComplete(player!!, "Death Plateau")) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage = (1..3).toIntArray().random() }
                1 -> npcl(FacialExpression.HAPPY, "I heard about what you did, thank you!").also { stage = END_DIALOGUE }
                2 -> npcl(FacialExpression.HAPPY, "Thank you so much!").also { stage = END_DIALOGUE }
                3 -> npcl(FacialExpression.HAPPY, "Surely we are safe now!").also { stage = END_DIALOGUE }
            }
        } else {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage = (1..3).toIntArray().random() }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello stranger.").also { stage = END_DIALOGUE }
                2 -> npcl(FacialExpression.FRIENDLY, "Hi!").also { stage = END_DIALOGUE }
                3 -> npcl(FacialExpression.FRIENDLY, "Welcome to Burthorpe!").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "Thanks!").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BREOCA_1084)
    }
}

