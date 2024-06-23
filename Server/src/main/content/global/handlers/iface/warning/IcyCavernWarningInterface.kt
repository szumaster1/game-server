package content.global.handlers.iface.warning

import core.api.closeInterface
import core.api.consts.Components
import core.api.sendMessage
import core.api.teleport
import core.game.interaction.InterfaceListener
import core.game.world.map.Location

class IcyCavernWarningInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.CWS_WARNING_1_574, 17) { player, _, _, _, _, _ ->
            closeInterface(player)
            sendMessage(player, "You venture into the icy cavern.")
            teleport(player, Location(3056, 9555, 0))
            return@on true
        }
    }
}
