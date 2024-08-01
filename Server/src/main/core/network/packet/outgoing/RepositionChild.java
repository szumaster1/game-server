package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.ChildPositionContext;

public final class RepositionChild implements OutgoingPacket<ChildPositionContext> {

    @Override
    public void send(ChildPositionContext context) {
        IoBuffer buffer = new IoBuffer(119)
            .putShortA(context.getPlayer().getInterfaceManager().getPacketCount(1))
            .putLEInt(context.getInterfaceId() << 16 | context.getChildId())
            .putShort(context.getPosition().x)
            .putShortA(context.getPosition().y);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getSession().write(buffer);
    }

}
