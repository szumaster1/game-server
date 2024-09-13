package core.net.producer

import core.net.EventProducer
import core.net.IoReadEvent
import core.net.IoSession
import core.net.IoWriteEvent
import core.net.event.HSReadEvent
import core.net.event.HSWriteEvent
import java.nio.ByteBuffer

/**
 * Produces I/O events for the handshake protocol.
 * @author Emperor
 */
class HSEventProducer : EventProducer {

    /**
     * Produces a reader event for the handshake protocol.
     *
     * @param session The IoSession associated with the event.
     * @param buffer The ByteBuffer containing the data to be read.
     * @return An IoReadEvent specific to the handshake protocol.
     */
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return HSReadEvent(session, buffer)
    }

    /**
     * Produces a writer event for the handshake protocol.
     *
     * @param session The IoSession associated with the event.
     * @param context The context information for the write event.
     * @return An IoWriteEvent specific to the handshake protocol.
     */
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return HSWriteEvent(session, context)
    }
}