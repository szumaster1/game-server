package content.global.handlers.iface

import core.game.component.Component
import core.game.interaction.InterfaceListener
import org.rs.consts.Components

/**
 * Handles the world map interface.
 */
class WorldMapInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.WORLDMAP_755, 3) { player, _, _, _, _, _ ->
            player.interfaceManager.openWindowsPane(Component(if (player.interfaceManager.isResizable) 746 else 548), 2)
            player.packetDispatch.sendRunScript(1187, "ii", 0, 0)
            player.updateSceneGraph(true)
            return@on true
        }
    }

}