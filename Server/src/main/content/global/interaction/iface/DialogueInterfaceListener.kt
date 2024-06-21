package content.global.interaction.iface

import core.api.consts.Components
import core.game.interaction.InterfaceListener
import core.network.packet.PacketRepository
import core.network.packet.context.ChildPositionContext
import core.network.packet.outgoing.RepositionChild

class DialogueInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        onOpen(Components.TUTORIAL_TEXT_372) { player, _ ->
            // Title
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.TUTORIAL_TEXT_372, 0, 25,20))
            // Message 0
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.TUTORIAL_TEXT_372, 1, 10,34))
            // Message 1
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.TUTORIAL_TEXT_372, 2, 10,49))
            // Message 2
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.TUTORIAL_TEXT_372, 3, 10,64))
            // Message 3
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.TUTORIAL_TEXT_372, 4, 10,79))
            return@onOpen true
        }

        onOpen(Components.DOUBLEOBJBOX_131) { player, _ ->
            // Text
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.DOUBLEOBJBOX_131, 1, 96, 25))
            // Continue
            PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, Components.DOUBLEOBJBOX_131, 3, 96, 98))
            return@onOpen true
        }
    }
}
