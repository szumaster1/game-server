package core.network.producer

import core.network.EventProducer
import core.network.IoReadEvent
import core.network.IoSession
import core.network.IoWriteEvent
import core.network.event.MSHSReadEvent
import core.network.event.MSHSWriteEvent

import java.nio.ByteBuffer

/**
 * Handles the Management server handshake event producing.
 * @author Emperor
 */
class MSHSEventProducer : EventProducer {

    // This method produces a reader event based on the IoSession and ByteBuffer provided.
    override fun produceReader(session: IoSession, buffer: ByteBuffer): IoReadEvent {
        return MSHSReadEvent(session, buffer)
    }

    // This method produces a writer event based on the IoSession and context provided.
    override fun produceWriter(session: IoSession, context: Any): IoWriteEvent {
        return MSHSWriteEvent(session, context)
    }
}