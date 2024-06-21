package content.region.misc.dialogue.keldagrim.workers

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE
import core.utilities.START_DIALOGUE

@Initializable
class FactoryWorkerDialogue1(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "I'm sorry, I'm looking for the blast furnace?").also { stage++ }

            1 -> npcl(FacialExpression.OLD_ANGRY1, "Do I look like a guide to you?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "No, you look like a hard-working dwarf, but can you please tell me where the blast furnace is?").also { stage++ }
            3 -> npcl(FacialExpression.OLD_DEFAULT, "Alright, just head down the stairs, it's easy to find.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Thanks.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FACTORY_WORKER_2172)
    }
}
