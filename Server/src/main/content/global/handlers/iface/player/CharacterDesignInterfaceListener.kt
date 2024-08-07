package content.global.handlers.iface.player

import content.region.misc.handlers.tutorial.CharacterDesign
import core.api.consts.Components
import core.game.interaction.InterfaceListener

/**
 * Character design interface listener
 *
 * @constructor Character design interface listener
 */
class CharacterDesignInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.APPEARANCE_771) { player, _, _, buttonID, _, _ ->
            CharacterDesign.handleButtons(player, buttonID)
            return@on true
        }
    }
}
