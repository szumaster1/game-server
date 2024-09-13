package core.net.event

import core.cache.misc.buffer.ByteBufferUtils
import core.net.IoReadEvent
import core.net.IoSession
import java.nio.ByteBuffer

/**
 * Handles JS-5 reading events.
 * @author Emperor
 */
class JS5ReadEvent(session: IoSession, buffer: ByteBuffer) : IoReadEvent(session, buffer) {

    override fun read(session: IoSession, buffer: ByteBuffer) {
        while (buffer.hasRemaining()) {
            val opcode = buffer.get().toInt() and 0xFF

            if (buffer.remaining() < 3) {
                queueBuffer(opcode)
                return
            }

            when (opcode) {
                0, 1 -> {
                    val request = ByteBufferUtils.getMedium(buffer)
                    val container = request shr 16 and 0xFF
                    val archive = request and 0xFFFF
                    session.js5Queue.queue(container, archive, opcode == 1)
                }
                2, 3 -> {
                    buffer.get()
                    buffer.short
                }
                4 -> {
                    session.js5Encryption = buffer.get().toInt()
                    if (buffer.short != 0.toShort()) {
                        session.disconnect()
                        return
                    }
                }
                5, 9 -> {
                    if (buffer.remaining() < 4) {
                        queueBuffer(opcode)
                        return
                    }
                    buffer.int
                }
                6 -> {
                    ByteBufferUtils.getMedium(buffer) // Value should be 3
                    // buffer.short // Value should be 0
                }
                7 -> {
                    buffer.get()
                    buffer.short
                    session.disconnect()
                    return
                }
                else -> {
                    buffer.get()
                    buffer.short
                }
            }
        }
    }
}
