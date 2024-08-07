package content.region.misc.dialogue.keldagrim.workers

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Factory worker dialogue3.
 */
@Initializable
class FactoryWorkerDialogue3(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "What are you dwarves doing in this factory?").also { stage++ }
            1 -> npcl(FacialExpression.OLD_ANGRY1, "Working of course, can't you see that?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "But working on what?").also { stage++ }
            3 -> npcl(FacialExpression.OLD_DEFAULT, "Refining the ore that is being brought into the factory, of course.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "And what does that mean?").also { stage++ }
            5 -> npcl(FacialExpression.OLD_ANGRY1, "It means you should stop asking so many questions and get back to work!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FACTORY_WORKER_2174)
    }
}
