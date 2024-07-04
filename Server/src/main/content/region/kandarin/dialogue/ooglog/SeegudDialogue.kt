package content.region.kandarin.dialogue.ooglog

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.START_DIALOGUE

@Initializable
class SeegudDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.NEUTRAL, "Hello, there! Nice day, isn't it?").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Hmph, it an okay day. Not so sure is nice day. Bit sticky, bit hot. Makes my bones itch.").also { stage++ }
            2 -> playerl(FacialExpression.NEUTRAL, "Makes your bones...itch? How does that work?").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "When you get old, you understand.").also { stage++ }
            4 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SEEGUD_7052)
    }
}
