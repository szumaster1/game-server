package core.network.producer;

import core.network.EventProducer;
import core.network.IoReadEvent;
import core.network.IoSession;
import core.network.IoWriteEvent;
import core.network.event.JS5ReadEvent;
import core.network.event.JS5WriteEvent;

import java.nio.ByteBuffer;

/**
 * Produces JS-5 I/O events.
 * @author Tyler
 * @author Emperor
 */
public class JS5EventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new JS5ReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new JS5WriteEvent(session, context);
	}

}
