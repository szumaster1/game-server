package content.global.handlers.iface

import content.region.misc.tutorial.handlers.CharacterDesign
import core.game.interaction.InterfaceListener
import org.rs.consts.Components

/**
 * Represents a listener to handle the character design interface.
 */
class CharacterDesignInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.APPEARANCE_771) { player, _, _, buttonID, _, _ ->
            CharacterDesign.handleButtons(player, buttonID)
            return@on true
        }
    }

}