package content.region.misthalin.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Fishmonger dialogue.
 */
@Initializable
class FishmongerEtceteriaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.FRIENDLY, "Welcome, ${player.getAttribute("fremennikname", "fremmyname")}. My fish is fresher than any in Miscellania.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                end()
                openNpcShop(player, NPCs.FISHMONGER_1369)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FISHMONGER_1369)
    }
}