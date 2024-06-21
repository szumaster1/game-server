package content.global.interaction.iface

import core.api.consts.Components
import core.game.interaction.InterfaceListener
import core.network.packet.PacketRepository
import core.network.packet.context.ChildPositionContext
import core.network.packet.outgoing.RepositionChild

class DoubleItemInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        onOpen(Components.SELECT_AN_OPTION_140) { player, _ ->
            // Left sword sprite.
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.SELECT_AN_OPTION_140, 0, 23, 5))
            // Left text box.
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.SELECT_AN_OPTION_140, 2, 31, 32))
            // Right text box.
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.SELECT_AN_OPTION_140, 3, 234, 32))
            // Title.
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.SELECT_AN_OPTION_140, 4, 24, 3))
            // Left model box.
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.SELECT_AN_OPTION_140, 5, 123, 36))
            // Right model box.
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.SELECT_AN_OPTION_140, 6, 334, 36))
            return@onOpen true
        }
    }
}
