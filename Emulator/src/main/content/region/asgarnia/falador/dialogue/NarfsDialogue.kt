package content.region.asgarnia.falador.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Narfs dialogue.
 */
@Initializable
class NarfsDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.HALF_GUILTY, "That's a funny name you've got.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "'Narf'? You think that's funny?", "At least I Don't call myself '" + player.username + "' ", "Where did you get a name like that?").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "It seemed like a good idea at the time!").also { stage++ }
            2 -> npc(FacialExpression.HALF_GUILTY, "Bah!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NARF_3238)
    }

}