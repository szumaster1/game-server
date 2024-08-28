package content.global.handlers.iface.player

import content.region.misc.tutorial.CharacterDesign
import cfg.consts.Components
import core.game.interaction.InterfaceListener

/**
 * Character design interface listener.
 */
class CharacterDesignInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.APPEARANCE_771) { player, _, _, buttonID, _, _ ->
            CharacterDesign.handleButtons(player, buttonID)
            return@on true
        }
    }
}
