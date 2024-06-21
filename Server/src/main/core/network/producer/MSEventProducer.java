package core.network.producer;

import core.network.EventProducer;
import core.network.IoReadEvent;
import core.network.IoSession;
import core.network.IoWriteEvent;
import core.network.event.MSReadEvent;
import core.network.event.MSWriteEvent;

import java.nio.ByteBuffer;

/**
 * Handles Management server events.
 * @author Emperor
 */
public final class MSEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new MSReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new MSWriteEvent(session, context);
	}

}
