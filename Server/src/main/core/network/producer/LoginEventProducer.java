package core.network.producer;

import core.network.EventProducer;
import core.network.IoReadEvent;
import core.network.IoSession;
import core.network.IoWriteEvent;
import core.network.event.LoginWriteEvent;
import core.network.event.LoginReadEvent;

import java.nio.ByteBuffer;

/**
 * Produces login I/O events.
 * @author Emperor
 */
public final class LoginEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new LoginReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new LoginWriteEvent(session, context);
	}

}
