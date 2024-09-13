package core.net.event

import core.net.IoSession
import core.net.IoWriteEvent
import core.net.producer.JS5EventProducer
import core.net.producer.LoginEventProducer
import java.nio.ByteBuffer

/**
 * Handles Handshake write events.
 * @author Emperor
 */
class HSWriteEvent(session: IoSession, context: Any) : IoWriteEvent(session, context) {

    companion object {
        private val JS5_PRODUCER = JS5EventProducer()
        private val LOGIN_PRODUCER = LoginEventProducer()
    }

    override fun write(session: IoSession, context: Any) {
        val buffer = ByteBuffer.allocate(9)
        buffer.put(0)
        if (context as Boolean) {
            buffer.putLong(session.serverKey)
            session.producer = LOGIN_PRODUCER
        } else {
            session.producer = JS5_PRODUCER
        }
        buffer.flip()
        session.queue(buffer)
    }
}
