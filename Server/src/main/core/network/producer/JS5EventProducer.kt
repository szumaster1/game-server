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
 * @author Tyler
 * @author Emperor
 */
class JS5EventProducer : EventProducer {

    // his method produces a reader event based on the session and buffer provided.
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return JS5ReadEvent(session, buffer)
    }
    // This method produces a writer event based on the session and context provided.
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return JS5WriteEvent(session, context)
    }
}
