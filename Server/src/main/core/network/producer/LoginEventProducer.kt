package core.network.producer

import core.network.EventProducer
import core.network.IoReadEvent
import core.network.IoSession
import core.network.IoWriteEvent
import core.network.event.LoginReadEvent
import core.network.event.LoginWriteEvent
import java.nio.ByteBuffer

/**
 * Produces login I/O events.
 * @author Emperor
 */
class LoginEventProducer : EventProducer {

    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return LoginReadEvent(session, buffer)
    }

    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return LoginWriteEvent(session, context)
    }
}
