package core.net.producer

import core.net.EventProducer
import core.net.IoReadEvent
import core.net.IoSession
import core.net.IoWriteEvent
import core.net.event.RegistryReadEvent
import core.net.event.RegistryWriteEvent
import java.nio.ByteBuffer

/**
 * Handles world server registry.
 * @author Emperor
 */
class RegistryEventProducer : EventProducer {

    /**
     * Produce reader.
     *
     * @param session The IoSession associated with the read event.
     * @param buffer The ByteBuffer containing the data to be read.
     * @return An instance of IoReadEvent representing the read operation.
     */
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return RegistryReadEvent(session, buffer)
    }

    /**
     * Produce writer.
     *
     * @param session The IoSession associated with the write event.
     * @param context The context data to be written.
     * @return An instance of IoWriteEvent representing the write operation.
     */
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return RegistryWriteEvent(session, context)
    }
}
