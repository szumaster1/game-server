package content.region.fremennik.dialogue.miscellania

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Fullangr dialogue.
 */
@Initializable
class FullangrDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "Good day, sir.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What are you doing down here?", "Good day.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "What are you doing down here?").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "Good day.").also { stage = END_DIALOGUE }
            }
            2 -> npc(FacialExpression.OLD_DEFAULT, "I'm working on the digging, of course.", "It's a small excavation, so only two of us ", "can work on it at a time.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FULLANGR_3934)
    }
}
