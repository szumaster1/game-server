package core.network.producer;

import core.network.EventProducer;
import core.network.IoReadEvent;
import core.network.IoSession;
import core.network.IoWriteEvent;
import core.network.event.HSReadEvent;
import core.network.event.HSWriteEvent;

import java.nio.ByteBuffer;

/**
 * Produces I/O events for the handshake protocol.
 * @author Emperor
 */
public final class HSEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new HSReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new HSWriteEvent(session, context);
	}

}
