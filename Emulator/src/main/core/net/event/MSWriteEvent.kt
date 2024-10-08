package core.net.event

import core.net.IoSession
import core.net.IoWriteEvent
import core.net.packet.IoBuffer
import core.net.packet.PacketHeader
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
