package core.network.producer

import core.network.EventProducer
import core.network.IoReadEvent
import core.network.IoSession
import core.network.IoWriteEvent
import core.network.event.RegistryReadEvent
import core.network.event.RegistryWriteEvent
import java.nio.ByteBuffer

/**
 * Handles world server registry.
 * @author Emperor
 */
class RegistryEventProducer : EventProducer {

    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return RegistryReadEvent(session, buffer)
    }

    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return RegistryWriteEvent(session, context)
    }
}
