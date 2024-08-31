package core.network.event

import core.network.IoReadEvent
import core.network.IoSession
import core.network.producer.RegistryEventProducer
import java.nio.ByteBuffer

/**
 * Handles the management server handshake read events.
 * @author Emperor
 */
class MSHSReadEvent(session: IoSession, buffer: ByteBuffer) : IoReadEvent(session, buffer) {

    companion object {
        private val REGISTRY_PRODUCER = RegistryEventProducer()
    }

    override fun read(session: IoSession, buffer: ByteBuffer) {
        val opcode = buffer.get().toInt() and 0xFF
        if (opcode == 14) {
            session.producer = REGISTRY_PRODUCER
            session.write(true)
        }
    }
}
