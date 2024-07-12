package core.network.producer

import core.network.EventProducer
import core.network.IoReadEvent
import core.network.IoSession
import core.network.IoWriteEvent
import core.network.event.JS5ReadEvent
import core.network.event.JS5WriteEvent
import java.nio.ByteBuffer

/**
 * Produces JS-5 I/O events.
 * @author Emperor, Tyler
 */
class JS5EventProducer : EventProducer {

    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return JS5ReadEvent(session, buffer)
    }

    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return JS5WriteEvent(session, context)
    }
}
