package content.global.handlers.iface.player

import org.rs.consts.Components
import core.game.component.Component
import core.game.interaction.InterfaceListener

/**
 * World map interface listener.
 */
class WorldMapInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.WORLDMAP_755, 3){ player, _, _, _, _, _ ->
            player.interfaceManager.openWindowsPane(Component(if (player.interfaceManager.isResizable) 746 else 548), 2)
            player.packetDispatch.sendRunScript(1187, "ii", 0, 0)
            player.updateSceneGraph(true)
            return@on true
        }
    }
}
