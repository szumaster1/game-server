package core.net.packet.outgoing;

import core.net.packet.IoBuffer;
import core.net.packet.OutgoingPacket;
import core.net.packet.PacketHeader;
import core.net.packet.context.InteractionOptionContext;

/**
 * Interaction option.
 * @author Emperor
 */
public final class InteractionOption implements OutgoingPacket<InteractionOptionContext> {

    @Override
    public void send(InteractionOptionContext context) {
        IoBuffer buffer = new IoBuffer(44, PacketHeader.BYTE);
        buffer.putLEShortA(-1);
        if (context.isRemove()) {
            buffer.put(0);
        } else {
            buffer.put(context.index == 0 ? 1 : 0);
        }
        buffer.put(context.index + 1);
        buffer.putString(context.name);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().output);
        context.getPlayer().getSession().write(buffer);
    }

}
