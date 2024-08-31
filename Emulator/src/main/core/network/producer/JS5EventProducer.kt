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
 * @author Tyler, Emperor
 */
class JS5EventProducer : EventProducer {

    /**
     * Produce reader.
     *
     * @param session The IoSession associated with the read event.
     * @param buffer The ByteBuffer containing the data to be read.
     * @return An instance of IoReadEvent representing the read operation.
     */
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return JS5ReadEvent(session, buffer)
    }

    /**
     * Produce writer.
     *
     * @param session The IoSession associated with the write event.
     * @param context The context data to be written.
     * @return An instance of IoWriteEvent representing the write operation.
     */
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return JS5WriteEvent(session, context)
    }
}