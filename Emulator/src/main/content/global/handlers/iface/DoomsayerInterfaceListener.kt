package content.global.handlers.iface

import org.rs.consts.Components
import core.game.interaction.InterfaceListener

/**
 * Represents the interface listener for the warning messages.
 */
class DoomsayerInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.CWS_DOOMSAYER_583) { player, _, _, buttonID, _, _ ->
            if (buttonID in 46..85) {
                player.warningMessages.getMessage(buttonID).toggle(player)
            }
            return@on true
        }
    }

}
