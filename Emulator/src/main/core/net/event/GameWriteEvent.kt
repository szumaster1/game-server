package core.net.event

import core.net.IoSession
import core.net.IoWriteEvent
import core.net.packet.IoBuffer
import core.net.packet.PacketHeader
import java.nio.ByteBuffer

/**
 * Handles game packet writing events.
 * @author Emperor
 */
class GameWriteEvent(session: IoSession?, context: Any?) : IoWriteEvent(session, context) {

    override fun write(session: IoSession, context: Any) {
        if (context is ByteBuffer) {
            session.queue(context)
            return
        }
        val buffer = context as IoBuffer
        var buf = buffer.toByteBuffer().flip() as ByteBuffer
            ?: throw RuntimeException("Critical networking error: The byte buffer requested was null.")
        if (buffer.opcode() != -1) {
            val packetLength = buf.remaining() + 4
            val response = ByteBuffer.allocate(packetLength)
            response.put(buffer.opcode().toByte())
            when (buffer.header) {
                PacketHeader.BYTE -> response.put(buf.remaining().toByte())
                PacketHeader.SHORT -> response.putShort(buf.remaining().toShort())
                else -> {}
            }
            buf = response.put(buf).flip() as ByteBuffer
        }
        session.queue(buf)
    }
}
