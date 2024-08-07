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

    // This method produces a reader event based on the IoSession and ByteBuffer provided.
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return MSReadEvent(session, buffer)
    }

    // This method produces a writer event based on the IoSession and context provided.
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return MSWriteEvent(session, context)
    }
}

