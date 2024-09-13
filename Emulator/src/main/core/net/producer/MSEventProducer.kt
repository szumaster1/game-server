package core.net.producer

import core.net.EventProducer
import core.net.IoReadEvent
import core.net.IoSession
import core.net.IoWriteEvent
import core.net.event.MSReadEvent
import core.net.event.MSWriteEvent
import java.nio.ByteBuffer

/**
 * Handles Management server events.
 * @author Emperor
 */
class MSEventProducer : EventProducer {

    /**
     * Produce reader.
     *
     * @param session The IoSession associated with the reader.
     * @param buffer The ByteBuffer that contains the data to be read.
     * @return An instance of IoReadEvent that represents the read operation.
     */
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return MSReadEvent(session, buffer)
    }

    /**
     * Produce writer.
     *
     * @param session The IoSession associated with the writer.
     * @param context The context object that contains data for writing.
     * @return An instance of IoWriteEvent that represents the write operation.
     */
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return MSWriteEvent(session, context)
    }
}

