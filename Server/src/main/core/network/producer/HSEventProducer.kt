package core.network.producer

import core.network.EventProducer
import core.network.IoReadEvent
import core.network.IoSession
import core.network.IoWriteEvent
import core.network.event.HSReadEvent
import core.network.event.HSWriteEvent
import java.nio.ByteBuffer

/**
 * Produces I/O events for the handshake protocol.
 * @author Emperor
 */
class HSEventProducer : EventProducer {

    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return HSReadEvent(session, buffer)
    }

    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return HSWriteEvent(session, context)
    }
}
