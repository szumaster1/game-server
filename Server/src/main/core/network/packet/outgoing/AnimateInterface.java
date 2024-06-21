package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.AnimateInterfaceContext;

/**
 * The animate interface outgoing packet.
 * @author Emperor
 */
public class AnimateInterface implements OutgoingPacket<AnimateInterfaceContext> {

	@Override
	public void send(AnimateInterfaceContext context) {
		IoBuffer buffer = new IoBuffer(36);
		buffer.putIntB((context.getInterfaceId() << 16) + context.getChildId());
		buffer.putLEShort(context.getAnimationId());
		buffer.putShortA(context.getPlayer().getInterfaceManager().getPacketCount(1));
		buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());context.getPlayer().getDetails().getSession().write(buffer);
	}
}
