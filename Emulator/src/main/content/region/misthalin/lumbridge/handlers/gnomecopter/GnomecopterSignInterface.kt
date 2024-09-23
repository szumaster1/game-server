package content.region.misthalin.lumbridge.handlers.gnomecopter

import core.api.closeTabInterface
import org.rs.consts.Components
import core.game.interaction.InterfaceListener

/**
 * Gnome copter sign interface listener.
 */
class GnomecopterSignInterface : InterfaceListener {

    override fun defineInterfaceListeners() {

        /*
         * Handle close sign interface.
         */

        on(Components.CARPET_INFO_723) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                11 -> closeTabInterface(player)
            }
            return@on true
        }
    }
}
