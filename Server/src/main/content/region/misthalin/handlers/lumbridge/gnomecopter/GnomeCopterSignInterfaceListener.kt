package content.region.misthalin.handlers.lumbridge.gnomecopter

import core.api.closeTabInterface
import core.api.consts.Components
import core.game.interaction.InterfaceListener

class GnomeCopterSignInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.CARPET_INFO_723) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                11 -> closeTabInterface(player)
            }
            return@on true
        }
    }
}
