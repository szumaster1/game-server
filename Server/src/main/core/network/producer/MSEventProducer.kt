package core.network.producer

import core.network.EventProducer
import core.network.IoReadEvent
import core.network.IoSession
import core.network.IoWriteEvent
import core.network.event.MSReadEvent
import core.network.event.MSWriteEvent
import java.nio.ByteBuffer

/**
 * Handles Management server events.
 * @author Emperor
 */
class MSEventProducer : EventProducer {

    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return MSReadEvent(session, buffer)
    }

    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return MSWriteEvent(session, context)
    }
}
