package core.network.producer;

import core.network.EventProducer;
import core.network.IoReadEvent;
import core.network.IoSession;
import core.network.IoWriteEvent;
import core.network.event.GameReadEvent;
import core.network.event.GameWriteEvent;

import java.nio.ByteBuffer;

/**
 * Produces game packet I/O events.
 * @author Emperor
 */
public final class GameEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new GameReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new GameWriteEvent(session, context);
	}

}
