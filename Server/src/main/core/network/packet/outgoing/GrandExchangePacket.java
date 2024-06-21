package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.PacketHeader;
import core.network.packet.context.GrandExchangeContext;

/**
 * The outgoing packet used for updating a player's grand exchange data.
 * @author Emperor
 * @author Vexia
 * @author Angle
 */
public class GrandExchangePacket implements OutgoingPacket<GrandExchangeContext> {

	private final int REMOVED = 6;
	private final int ABORTED = 2;

	@Override
	public void send(GrandExchangeContext context) {
		final IoBuffer buffer = new IoBuffer(116, PacketHeader.NORMAL);
		buffer.put(context.idx);
		if (context.state == REMOVED) {
			buffer.put(0).putShort(0).putInt(0).putInt(0).putInt(0).putInt(0);
		} else {
			byte state = (byte) (context.state + 1);
			if (context.isSell) {
				state += 8;
			}
			if (context.state == ABORTED) {
				state = context.isSell ? (byte) -3 : (byte) 5;
			}
			buffer.put(state).putShort(context.itemID).putInt(context.value).putInt(context.amt).
					putInt(context.completedAmt).putInt(context.totalCoinsExchanged);
		}
		try {
			buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());context.getPlayer().getSession().write(buffer);
		} catch (Exception e) {
		}
	}

}
