package core.network.event

import core.network.IoSession
import core.network.IoWriteEvent
import core.network.packet.IoBuffer
import core.network.packet.PacketHeader
import java.nio.ByteBuffer

/**
 * Handles management server write events.
 * @author Emperor
 */
class MSWriteEvent(session: IoSession?, context: Any?) : IoWriteEvent(session, context) {

    override fun write(session: IoSession, context: Any) {
        val buffer = buildPacketBuffer(context as IoBuffer)
        session.queue(buffer)
    }

    private fun buildPacketBuffer(b: IoBuffer): ByteBuffer {
        val size = b.toByteBuffer().position()
        val buffer = ByteBuffer.allocate(1 + size + b.header.ordinal)
        buffer.put(b.opcode().toByte())
        when (b.header) {
            PacketHeader.BYTE -> buffer.put(size.toByte())
            PacketHeader.SHORT -> buffer.putShort(size.toShort())
            else -> { } // PacketHeader.NORMAL
        }
        buffer.put(b.toByteBuffer().flip() as ByteBuffer)
        return buffer.flip() as ByteBuffer
    }
}
