package content.region.misthalin.dialogue.digsite

import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import core.api.consts.NPCs

@Initializable
class ResearcherDialogue(player: Player? = null) : Dialogue(player) {
    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (isQuestComplete(player, "The Dig Site")) {
            when (stage) {
                START_DIALOGUE -> npcl(FacialExpression.FRIENDLY, "Hello there. What are you doing here?").also { stage++ }
                1 -> playerl(FacialExpression.FRIENDLY, "Just looking around at the moment.").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Well, feel free to talk to me should you come across anything you can't figure out.").also { stage = END_DIALOGUE }
            }
        } else {
            when (stage) {
                START_DIALOGUE -> npcl(FacialExpression.FRIENDLY, "Hello there. What are you doing here?").also { stage++ }
                1 -> playerl(FacialExpression.FRIENDLY, "Just looking around at the moment.").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Well, feel free to talk to me should you come across anything you can't figure out.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RESEARCHER_4568)
    }
}
