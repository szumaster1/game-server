package content.region.karamja.quest.tribaltotem.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class GPDTEmployeeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npcl(FacialExpression.HAPPY, "Welcome to G.P.D.T.!")
        stage = if (player.questRepository.getStage("Tribal Totem") == 20) {
            5
        } else 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HAPPY, "Thank you very much.").also { stage = 1000 }

            5 -> playerl(FacialExpression.ASKING, "So, when are you going to deliver this crate?").also { stage++ }
            6 -> npcl(FacialExpression.THINKING, "Well... I guess we could do it now...").also {
                player.questRepository.getQuest("Tribal Totem").setStage(player, 25)
                stage = 1000
            }

            1000 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RPDT_EMPLOYEE_843)
    }
}