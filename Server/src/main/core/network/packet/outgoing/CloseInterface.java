package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.InterfaceContext;

/**
 * Close interface.
 * @author Emperor
 */
public final class CloseInterface implements OutgoingPacket<InterfaceContext> {

    @Override
    public void send(InterfaceContext context) {
        IoBuffer buffer = new IoBuffer(149);
        buffer.putShort(context.getPlayer().getInterfaceManager().getPacketCount(1));
        buffer.putShort(context.getWindowId());
        buffer.putShort(context.getComponentId());
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getSession().write(buffer);
    }

}
