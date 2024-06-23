package content.global.handlers.iface.warning

import core.api.closeInterface
import core.api.consts.Components
import core.api.runTask
import core.api.teleport
import core.game.interaction.InterfaceListener
import core.game.world.map.Location

class ObservatoryDungeonWarningInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.CWS_WARNING_9_560) { player, _, _, buttonID, _, _ ->
            if(buttonID == 17) {
                closeInterface(player)
                runTask(player, 2){
                    teleport(player, Location(2355, 9394, 0))
                }
            }
            return@on true
        }
    }
}
