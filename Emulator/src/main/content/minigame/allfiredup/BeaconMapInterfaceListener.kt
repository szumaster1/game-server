package content.minigame.allfiredup

import cfg.consts.Components
import core.game.interaction.InterfaceListener

/**
 * Beacon map interface listener.
 */
class BeaconMapInterfaceListener : InterfaceListener {

    companion object {
        const val BEACON_MAP_575 = Components.BEACON_MAP_575

    }

    override fun defineInterfaceListeners() {
        on(BEACON_MAP_575) { _, _, _, buttonID, _, _ ->
            return@on true
        }
    }
}
