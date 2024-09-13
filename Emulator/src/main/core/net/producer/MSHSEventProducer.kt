package core.net.producer

import core.net.EventProducer
import core.net.IoReadEvent
import core.net.IoSession
import core.net.IoWriteEvent
import core.net.event.MSHSReadEvent
import core.net.event.MSHSWriteEvent

import java.nio.ByteBuffer

/**
 * Handles the Management server handshake event producing.
 * @author Emperor
 */
class MSHSEventProducer : EventProducer {

    /**
     * Produce reader.
     *
     * @param session The IoSession associated with the read event.
     * @param buffer The ByteBuffer containing the data to be read.
     * @return An instance of IoReadEvent representing the read operation.
     */
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return MSHSReadEvent(session, buffer)
    }

    /**
     * Produce writer.
     *
     * @param session The IoSession associated with the write event.
     * @param context The context data to be written.
     * @return An instance of IoWriteEvent representing the write operation.
     */
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return MSHSWriteEvent(session, context)
    }
}