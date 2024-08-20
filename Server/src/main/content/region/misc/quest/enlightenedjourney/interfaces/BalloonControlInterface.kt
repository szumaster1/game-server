package content.region.misc.quest.enlightenedjourney.interfaces

import core.api.*
import core.api.consts.Components
import core.game.component.Component
import core.game.component.InterfaceType
import core.game.interaction.InterfaceListener

/**
 * Represents the Balloon control interface.
 */
class BalloonControlInterface : InterfaceListener {
    private val BACKGROUND = Components.ZEP_INTERFACE_470
    private val CONTROLS = Components.ZEP_INTERFACE_SIDE_471

    override fun defineInterfaceListeners() {
        /*
        onOpen(CONTROLS) { player, _ ->
            player.lock()
            Component(CONTROLS).definition.type = InterfaceType.SINGLE_TAB
            openOverlay(player, BACKGROUND)
            Component(BACKGROUND).definition.type = InterfaceType.OVERLAY
            setAttribute(player, "logs-button-click", 0)

            // Fuel X = 8 X = 10
            player.packetDispatch.sendLeftShiftedVarbit(913, 2, 42)
            return@onOpen true
        }

        onClose(CONTROLS) { player, _ ->
            closeOverlay(player).also { closeInterface(player) }
            return@onClose true
        }

        on(CONTROLS) { player, _, _, buttonID, _, _ ->
            val sandbagButton = 4
            val RelaxButton = 5
            val tugButton = 6
            val bailButton = 8
            val logsButton = 9
            val emergencyButton = 10

            if (buttonID == logsButton) {
                player.incrementAttribute("logs-button-click", 0)
            }

            when (buttonID) {
                logsButton -> if (getAttribute(player, "logs-button-click", 0) == 2) {
                    player.packetDispatch.sendLeftShiftedVarbit(913, 2, 38)
                } else {
                    if (getAttribute(player, "logs-button-click", 0) == 4) {
                        player.packetDispatch.sendLeftShiftedVarbit(913, 2, 34)
                    }
                }
            }
            return@on true
        }
    }
    */
    }
}