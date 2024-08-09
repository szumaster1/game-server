package content.global.handlers.iface

import core.api.consts.Components
import core.api.setInterfaceSprite
import core.game.interaction.InterfaceListener
import core.network.packet.PacketRepository
import core.network.packet.context.ChildPositionContext
import core.network.packet.outgoing.RepositionChild

/**
 * Interface repositioning listener.
 */
class InterfaceRepositioningListener : InterfaceListener {

    override fun defineInterfaceListeners() {

        onOpen(Components.TUTORIAL_TEXT_372) { player, _ ->
            // Title.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 0, 25,20)
            // String 0.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 1, 10,34)
            // String 1.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 2, 10,49)
            // String 2.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 3, 10,64)
            // String 3.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 4, 10,79)
            return@onOpen true
        }

        onOpen(Components.DOUBLEOBJBOX_131) { player, _ ->
            // String.
            setInterfaceSprite(player, Components.DOUBLEOBJBOX_131, 1, 96, 25)
            // Continue button.
            setInterfaceSprite(player, Components.DOUBLEOBJBOX_131, 3, 96, 98)
            return@onOpen true
        }

        onOpen(Components.SELECT_AN_OPTION_140) { player, _ ->
            // Left sword sprite.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 0, 23, 5)
            // Left text box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 2, 31, 32)
            // Right text box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 3, 234, 32)
            // Title.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 4, 24, 3)
            // Left model box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 5, 123, 36)
            // Right model box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 6, 334, 36)
            return@onOpen true
        }

    }

}
