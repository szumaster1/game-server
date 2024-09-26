package content.global.handlers.iface

import core.api.setInterfaceSprite
import core.game.interaction.InterfaceListener
import org.rs.consts.Components

class DoubleObjectInterface: InterfaceListener {

    override fun defineInterfaceListeners() {
        onOpen(Components.DOUBLEOBJBOX_131) { player, _ ->
            setInterfaceSprite(player, Components.DOUBLEOBJBOX_131, 1, 96, 25)// String.
            setInterfaceSprite(player, Components.DOUBLEOBJBOX_131, 3, 96, 98)// Continue button.
            return@onOpen true
        }
    }

}