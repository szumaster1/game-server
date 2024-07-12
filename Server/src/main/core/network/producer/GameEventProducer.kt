package core.network.producer

import core.network.EventProducer
import core.network.IoReadEvent
import core.network.IoSession
import core.network.IoWriteEvent
import core.network.event.GameReadEvent
import core.network.event.GameWriteEvent
import java.nio.ByteBuffer

/**
 * Produces game packet I/O events.
 * @author Emperor
 */
class GameEventProducer : EventProducer {

    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return GameReadEvent(session, buffer)
    }

    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return GameWriteEvent(session, context)
    }
}
