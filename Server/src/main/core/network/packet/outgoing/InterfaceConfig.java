package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.InterfaceConfigContext;

public class InterfaceConfig implements OutgoingPacket<InterfaceConfigContext> {

    @Override
    public void send(InterfaceConfigContext context) {
        IoBuffer buffer = new IoBuffer(21);
        buffer.putC(context.isHidden() ? 1 : 0);
        buffer.putShort(context.getPlayer().getInterfaceManager().getPacketCount(1));
        buffer.putLEInt(context.getInterfaceId() << 16 | context.getChildId());
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getSession().write(buffer);
    }
}
