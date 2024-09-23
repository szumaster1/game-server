package content.global.travel.glider

import core.api.setVarp
import core.api.submitWorldPulse
import core.api.unlock
import core.game.interaction.InterfaceListener
import org.rs.consts.Components

/**
 * Represents the Glider interface.
 */
class GliderInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        onOpen(Components.GLIDERMAP_138) { player, _ ->
            setVarp(player, 153, 0)
            return@onOpen true
        }


        on(Components.GLIDERMAP_138) { player, _, _, buttonID, _, _ ->
            val glider = Gliders.forId(buttonID) ?: return@on true
            when (buttonID) {
                glider.button -> submitWorldPulse(GliderPulse(1, player, glider))
            }
            return@on true
        }

        onClose(Components.GLIDERMAP_138) { player, _ ->
            unlock(player)
            return@onClose true
        }
    }
}