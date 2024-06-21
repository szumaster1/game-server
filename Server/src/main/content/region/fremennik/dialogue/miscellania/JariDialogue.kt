package content.region.fremennik.dialogue.miscellania

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class JariDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "Good day, sir.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What are you doing down here?", "Good day.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "What are you doing down here?").also { stage = 10 }
                2 -> player(FacialExpression.NEUTRAL, "Good day.").also { stage = END_DIALOGUE }
            }
            10 -> npc(FacialExpression.OLD_DEFAULT, "I'm waiting to work on the digging.").also { stage++ }
            11 -> npc(FacialExpression.OLD_HAPPY, "It's the first excavation I've worked on, ", "and I'm looking forward to it.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JARI_3935)
    }
}
