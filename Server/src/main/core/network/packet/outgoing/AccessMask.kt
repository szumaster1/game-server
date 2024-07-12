package core.network.packet.outgoing

import core.network.packet.IoBuffer
import core.network.packet.OutgoingPacket
import core.network.packet.context.AccessMaskContext

/**
 * The access mask outgoing packet.
 * @author Emperor
 */
class AccessMask : OutgoingPacket<AccessMaskContext> {

    override fun send(context: AccessMaskContext) {
        val buffer = IoBuffer(165)
        buffer.putLEShort(context.player.interfaceManager.getPacketCount(1))
        buffer.putLEShort(context.length)
        buffer.putInt(context.interfaceId shl 16 or context.childId)
        buffer.putShortA(context.offset)
        buffer.putIntA(context.id)
        buffer.cypherOpcode(context.player.session.isaacPair!!.output)
        context.player.session.write(buffer)
    }
}
