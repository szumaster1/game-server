package content.region.fremennik.dialogue.miscellania

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Thorodin dialogue.
 */
@Initializable
class ThorodinDialogue(player: Player? = null): Dialogue(player) {

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
            2 -> npc(FacialExpression.OLD_DEFAULT, "We're extending the cave so more people can live in it.", "These Miscellanians aren't so bad.", "They appreciate the benefits of living underground.").also { stage++ }
            3 -> player(FacialExpression.ASKING, "...such as?").also { stage++ }
            4 -> npc(FacialExpression.OLD_DEFAULT, "Not getting rained on, for example.", "Did you do anything about that monster Donal", "was talking about?").also { stage++ }
            5 -> player(FacialExpression.FRIENDLY, "It's been taken care of.").also { stage++ }
            6 -> npc(FacialExpression.OLD_HAPPY, "Glad to hear it.", "Now we can get on with excavating.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.THORODIN_3936)
    }
}
