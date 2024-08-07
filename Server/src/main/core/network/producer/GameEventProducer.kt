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
 * This class implements the EventProducer interface to generate IoReadEvent and IoWriteEvent instances for game packets.
 * @author Emperor
 */
class GameEventProducer : EventProducer {
    /**
     * Produces an IoReadEvent for reading game packets.
     * @param session The IoSession for the current session.
     * @param buffer The ByteBuffer containing the packet data.
     * @return A new GameReadEvent instance for processing the read event.
     */
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return GameReadEvent(session, buffer)
    }

    /**
     * Produces an IoWriteEvent for writing game packets.
     * @param session The IoSession for the current session.
     * @param context The context or data to be written.
     * @return A new GameWriteEvent instance for processing the write event.
     */
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return GameWriteEvent(session, context)
    }
}
