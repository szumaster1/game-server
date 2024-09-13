package core.net.event

import core.Configuration
import core.cache.misc.buffer.ByteBufferUtils
import core.net.IoSession
import core.net.IoWriteEvent
import java.nio.ByteBuffer

/**
 * Handles the management server handshake write event.
 * @author Emperor
 */
class MSHSWriteEvent(session: IoSession, context: Any) : IoWriteEvent(session, context) {

    override fun write(session: IoSession, context: Any) {
        val buffer = ByteBuffer.allocate(2 + Configuration.MS_SECRET_KEY.length)
        buffer.put(88.toByte())
        ByteBufferUtils.putString(Configuration.MS_SECRET_KEY, buffer)
        session.queue(buffer.flip() as ByteBuffer)
    }
}