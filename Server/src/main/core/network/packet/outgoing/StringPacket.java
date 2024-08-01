package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.PacketHeader;
import core.network.packet.context.StringContext;

public class StringPacket implements OutgoingPacket<StringContext> {

    @Override
    public void send(StringContext context) {
        IoBuffer buffer = new IoBuffer(171, PacketHeader.SHORT);
        buffer.putIntB((context.getInterfaceId() << 16) | context.getLineId());
        buffer.putString(context.getString());
        buffer.putShortA(context.getPlayer().getInterfaceManager().getPacketCount(1));
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getDetails().getSession().write(buffer);
    }
}
