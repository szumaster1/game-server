package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.InterfaceContext;

public class ResetInterface implements OutgoingPacket<InterfaceContext> {
    @Override
    public void send(InterfaceContext ic) {
        IoBuffer buffer = new IoBuffer(149);
        buffer.putShort(ic.getPlayer().getInterfaceManager().getPacketCount(1));
        buffer.putInt(ic.getInterfaceId());
        ic.getPlayer().getSession().write(buffer);
    }
}
