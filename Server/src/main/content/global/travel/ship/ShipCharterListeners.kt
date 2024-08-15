package content.global.travel.ship

import core.api.consts.Components
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener

/**
 * Represents the chip chartering interface plugin.
 * @author Vexia
 */
class ShipCharterListeners : InteractionListener, InterfaceListener {

    override fun defineListeners() {
        on(SAILORS, IntType.NPC, "charter") { player, _ ->
            ShipCharter.open(player)
            return@on true
        }
    }

    override fun defineInterfaceListeners() {
        on(Components.SAILING_TRANSPORT_WORLD_MAP_95){ player, _, _, buttonID, _, _ ->
            ShipCharter.handle(player, buttonID)
            return@on true
        }
    }

    companion object {
        private val SAILORS = intArrayOf(NPCs.TRADER_STAN_4650, NPCs.TRADER_CREWMEMBER_4651, NPCs.TRADER_CREWMEMBER_4652, NPCs.TRADER_CREWMEMBER_4653, NPCs.TRADER_CREWMEMBER_4654, NPCs.TRADER_CREWMEMBER_4655, NPCs.TRADER_CREWMEMBER_4656)
    }

}
