package content.global.travel.charter

import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import org.rs.consts.Components
import org.rs.consts.NPCs

class CharterShipListener: InteractionListener, InterfaceListener {

    override fun defineListeners() {
        on(SAILORS, IntType.NPC, "charter") { player, _ ->
            CharterShipUtils.open(player)
            return@on true
        }
    }

    override fun defineInterfaceListeners() {
        on(Components.SAILING_TRANSPORT_WORLD_MAP_95){ player, _, _, buttonID, _, _ ->
            CharterShipUtils.handle(player, buttonID)
            return@on true
        }
    }

    companion object {
        private val SAILORS = intArrayOf(NPCs.TRADER_STAN_4650, NPCs.TRADER_CREWMEMBER_4651, NPCs.TRADER_CREWMEMBER_4652, NPCs.TRADER_CREWMEMBER_4653, NPCs.TRADER_CREWMEMBER_4654, NPCs.TRADER_CREWMEMBER_4655, NPCs.TRADER_CREWMEMBER_4656)
    }

}
