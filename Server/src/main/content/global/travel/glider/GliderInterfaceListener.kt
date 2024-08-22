package content.global.travel.glider

import content.global.travel.glider.GnomeGliderListeners.GliderPulse
import cfg.consts.Components
import core.api.submitWorldPulse
import core.api.unlock
import core.game.interaction.InterfaceListener

/**
 * Glider interface listener.
 */
class GliderInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.GLIDERMAP_138) { player, _, _, buttonID, _, _ ->
            val glider = GnomeGlider.forId(buttonID) ?: return@on true
            submitWorldPulse(GliderPulse(1, player, glider))
            return@on true
        }

        onClose(Components.GLIDERMAP_138) { player, _ ->
            unlock(player)
            return@onClose true
        }
    }
}