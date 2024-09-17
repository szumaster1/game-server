package content.region.fremennik.rellekka.dialogue

import org.rs.consts.NPCs
import core.api.getAttribute
import core.api.isQuestComplete
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Fur trader dialogue.
 */
@Initializable
class FurTraderDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Fremennik Trials")) {
            npc(FacialExpression.ANNOYED, "I don't sell to outerlanders.").also { stage = END_DIALOGUE }
        } else {
            npcl(FacialExpression.FRIENDLY, "Welcome back, ${getAttribute(player, "fremennikname", "fremmyname")}. Have you seen the furs I have today?")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                end()
                openNpcShop(player, NPCs.FUR_TRADER_1316)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FUR_TRADER_1316)
    }
}
