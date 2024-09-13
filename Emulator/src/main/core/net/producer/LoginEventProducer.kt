package core.net.producer

import core.net.EventProducer
import core.net.IoReadEvent
import core.net.IoSession
import core.net.IoWriteEvent
import core.net.event.LoginReadEvent
import core.net.event.LoginWriteEvent
import java.nio.ByteBuffer

/**
 * Produces login I/O events.
 * @author Emperor
 */
class LoginEventProducer : EventProducer {

    /**
     * Produce reader.
     *
     * @param session The IoSession associated with the read event.
     * @param buffer The ByteBuffer containing the data to be read.
     * @return An instance of IoReadEvent representing the read operation.
     */
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return LoginReadEvent(session, buffer)
    }

    /**
     * Produce writer.
     *
     * @param session The IoSession associated with the write event.
     * @param context The context data to be written.
     * @return An instance of IoWriteEvent representing the write operation.
     */
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return LoginWriteEvent(session, context)
    }
}