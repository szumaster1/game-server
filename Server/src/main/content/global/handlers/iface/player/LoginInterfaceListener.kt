package content.global.handlers.iface.player

import core.api.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.info.login.LoginConfiguration
import core.game.system.task.Pulse

class LoginInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.WELCOME_SCREEN_378) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                140 -> {
                    if (player.locks.isLocked("login")) return@on true
                    player.locks.lock("login", 2)
                    player.interfaceManager.close()
                    player.pulseManager.run(object : Pulse(1) {
                        override fun pulse(): Boolean {
                            LoginConfiguration.configureGameWorld(player)
                            return true
                        }
                    })
                }
                145 -> {}
                204 -> {}
            }
            return@on true
        }
    }
}
