package core.network.packet.outgoing

import core.network.packet.IoBuffer
import core.network.packet.OutgoingPacket
import core.network.packet.context.AnimateInterfaceContext

/**
 * The animate interface outgoing packet.
 * @author Emperor
 */
class AnimateInterface : OutgoingPacket<AnimateInterfaceContext> {

    override fun send(context: AnimateInterfaceContext) {
        val buffer = IoBuffer(36)
        buffer.putIntB((context.interfaceId shl 16) + context.childId)
        buffer.putLEShort(context.animationId)
        buffer.putShortA(context.player.interfaceManager.getPacketCount(1))
        buffer.cypherOpcode(context.player.session.isaacPair.output)
        context.player.details.session.write(buffer)
    }
}
