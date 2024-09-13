package content.global.skill.combat.summoning.familiar

import core.game.container.Container
import core.game.container.ContainerEvent
import core.game.container.ContainerListener
import core.game.node.entity.player.Player
import core.net.packet.PacketRepository
import core.net.packet.context.ContainerContext
import core.net.packet.outgoing.ContainerPacket

/**
 * Burden container listener.
 */
class BurdenContainerListener(val player: Player?) : ContainerListener {

    override fun update(c: Container?, event: ContainerEvent?) {
        PacketRepository.send(
            ContainerPacket::class.java,
            player?.let { ContainerContext(it, -1, -2, 30, event!!.items, false, *event.slots) }
        )
    }

    override fun refresh(c: Container?) {
        PacketRepository.send(
            ContainerPacket::class.java,
            player?.let { ContainerContext(it, -1, -2, 30, c!!.toArray(), c.capacity(), false) }
        )
    }
}

