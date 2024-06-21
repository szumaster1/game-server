package core.network.producer;

import core.network.EventProducer;
import core.network.IoReadEvent;
import core.network.IoSession;
import core.network.IoWriteEvent;
import core.network.event.MSHSReadEvent;
import core.network.event.MSHSWriteEvent;

import java.nio.ByteBuffer;

/**
 * Handles the Management server handshake event producing.
 * @author Emperor
 */
public final class MSHSEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new MSHSReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new MSHSWriteEvent(session, context);
	}

}
