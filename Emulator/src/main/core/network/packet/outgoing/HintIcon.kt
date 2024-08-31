package core.network.packet.outgoing

import core.network.packet.IoBuffer
import core.network.packet.OutgoingPacket
import core.network.packet.context.HintIconContext

/**
 * Handles the Hint icon outgoing packet.
 * @author Emperor
 */
class HintIcon : OutgoingPacket<HintIconContext> {

    override fun send(context: HintIconContext) {
        val buffer = IoBuffer(217)
        buffer.put(context.slot shl 6 or context.targetType).put(context.arrowId)
        if (context.arrowId > 0) {
            when (context.targetType) {
                1, 10 -> {// Skip 3 bytes
                    buffer.putShort(context.index).putShort(0).put(0)
                }
                else -> {
                    context.location?.let {
                        buffer.putShort(it.x).putShort(it.y).put(it.z)
                    } ?: run {// Skip all bytes
                        buffer.putShort(0).putShort(0).put(0)
                    }
                }
            }
            buffer.putShort(context.modelId)
        }
        buffer.cypherOpcode(context.player.session.isaacPair!!.output)
        context.player.session.write(buffer)
    }
}


