package content.region.asgarnia.falador.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Apprentice workman dialogue.
 */
@Initializable
class ApprenticeWorkmanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hiya.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.SAD, "Sorry, I haven't got time to chat. We've only just", "finished a collossal order of furniture for the Varrock", "area, and already there's more work coming in.").also { stage++ }
            1 -> player(FacialExpression.ASKING, "Varrock?").also { stage++ }
            2 -> npc(FacialExpression.ROLLING_EYES, "Yeah, the Council's had it redecorated.").also { stage++ }
            3 -> npc(NPCs.WORKMAN_3236, "Oi - stop gabbing and get that chair finished!").also { stage++ }
            4 -> npc(FacialExpression.SAD, "You'd better let me get on with my work.").also { stage++ }
            5 -> player(FacialExpression.NEUTRAL, "Ok, bye.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.APPRENTICE_WORKMAN_3235)
    }

}
