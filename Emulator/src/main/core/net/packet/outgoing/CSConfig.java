package core.net.packet.outgoing;

import core.net.packet.IoBuffer;
import core.net.packet.OutgoingPacket;
import core.net.packet.context.CSConfigContext;

/**
 * Cs config.
 * @author Emperor
 */
public class CSConfig implements OutgoingPacket<CSConfigContext> {

    @Override
    public void send(CSConfigContext context) {
        IoBuffer buffer = new IoBuffer(65);
        buffer.putLEShort(context.getPlayer().getInterfaceManager().getPacketCount(1));
        buffer.putC((byte) context.value);
        buffer.putLEShortA(context.id);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().output);
        context.getPlayer().getDetails().getSession().write(buffer);
    }

	/*@Override
	public void send(CSConfigContext context) {
		IoBuffer buffer = new IoBuffer(115);
		buffer.putShort(context.getId());
		buffer.putString(context.getTypes());

		for (int i = context.getTypes().length() - 1; i >= 0; i--) {
			if (context.getTypes().charAt(i) == 's') {
				buffer.putString((String) context.getParameters()[i]);
			} else {
				buffer.putInt(((Number) context.getParameters()[i]).intValue());
			}
		}

		buffer.putInt(context.getValue());
		context.getPlayer().getDetails().getSession().write(buffer);
	}*/
}
