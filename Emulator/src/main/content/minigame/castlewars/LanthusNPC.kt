package content.minigame.castlewars

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the Lanthus NPC.
 * @author Dginovoker
 */
class LanthusNPC : InteractionListener //, TickListener
{
    override fun defineListeners() {
        on(NPCs.LANTHUS_1526, IntType.NPC, "trade-with") { player, _ ->
            openNpcShop(player, NPCs.LANTHUS_1526)
            return@on true
        }
    }
}