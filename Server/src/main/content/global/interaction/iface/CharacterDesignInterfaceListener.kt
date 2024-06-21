package content.global.interaction.iface

import content.region.misc.handlers.tutorial.CharacterDesign
import core.api.consts.Components
import core.game.interaction.InterfaceListener

class CharacterDesignInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.APPEARANCE_771) { player, _, _, buttonID, _, _ ->
            CharacterDesign.handleButtons(player, buttonID)
            return@on true
        }
    }
}
