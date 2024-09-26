package content.global.handlers.iface

import core.api.setInterfaceSprite
import core.game.interaction.InterfaceListener
import org.rs.consts.Components

class SelectOptionInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        onOpen(Components.SELECT_AN_OPTION_140) { player, _ ->
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 0, 23, 5)// Left sword sprite.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 2, 31, 32)// Left text box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 3, 234, 32)// Right text box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 4, 24, 3)// Title.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 5, 123, 36)// Left model box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 6, 334, 36)// Right model box.
            return@onOpen true
        }
    }

}