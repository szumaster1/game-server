package content.region.misthalin.dialogue.varrock.museum

import cfg.consts.NPCs
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Teacherand pupil museum male dialogue.
 */
@Initializable
class TeacherandPupilMuseumMaleDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        sendNPCDialogue(player, NPCs.SCHOOLBOY_5949, "Teacher! Sir! I need the toilet!", FacialExpression.CHILD_GUILTY)
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendNPCDialogue(player, NPCs.TEACHER_AND_PUPIL_5948, "I told you to go before we got here.", FacialExpression.HALF_GUILTY).also { stage++ }
            1 -> sendNPCDialogue(player, NPCs.SCHOOLBOY_5949, "But sir, I didn't need to go then!", FacialExpression.CHILD_GUILTY).also { stage++ }
            2 -> sendNPCDialogue(player, NPCs.TEACHER_AND_PUPIL_5948, "Alright, come on then.", FacialExpression.HALF_GUILTY).also { stage = END_DIALOGUE }
        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TEACHER_AND_PUPIL_5944)
    }

}
