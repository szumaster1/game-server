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

    // Override method to produce a reader event.
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return RegistryReadEvent(session, buffer)
    }

    // Override method to produce a writer event.
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return RegistryWriteEvent(session, context)
    }
}
