package content.global.interaction.iface.warning

import core.api.closeInterface
import core.api.consts.Components
import core.api.getScenery
import core.api.sendMessage
import core.api.sendMessageWithDelay
import core.game.global.action.DoorActionHandler
import core.game.interaction.InterfaceListener

class MortMyreGateWarningInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.CWS_WARNING_20_580, 17) { player, _, _, buttonID, _, _ ->
            closeInterface(player)
            sendMessage(player, "You pass through the holy barrier.")
            DoorActionHandler.handleAutowalkDoor(player, if (player.location.x > 3443) getScenery(3444, 3458, 0) else getScenery(3443, 3458, 0))
            sendMessageWithDelay(player, "You walk into the gloomy atmosphere of Mort Myre.", 3)
            return@on true
        }
    }
}
