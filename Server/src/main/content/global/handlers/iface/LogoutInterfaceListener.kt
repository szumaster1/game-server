package content.global.handlers.iface

import core.api.consts.Components
import core.api.sendMessage
import core.game.interaction.InterfaceListener
import core.game.world.repository.Repository.disconnectionQueue

class LogoutInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.LOGOUT_182){ player, _, _, _, _, _ ->
            if (!player.zoneMonitor.canLogout()) {
                return@on true
            }
            if (player.inCombat()) {
                sendMessage(player, "You can't log out until 10 seconds after the end of combat.")
                return@on true
            }
            if (player.isTeleporting) {
                sendMessage(player, "Please finish your teleport before logging out.")
                return@on true
            }
            disconnectionQueue.add(player)
            return@on true
        }
    }
}
