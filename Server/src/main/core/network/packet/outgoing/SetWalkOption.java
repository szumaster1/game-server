package core.network.packet.outgoing;

import core.network.packet.OutgoingPacket;
import core.network.packet.context.WalkOptionContext;

public final class SetWalkOption implements OutgoingPacket<WalkOptionContext> {

    @Override
    public void send(WalkOptionContext context) {
        //    IoBuffer buffer = new IoBuffer(10, PacketHeader.BYTE).putString(context.getOption());
        //    context.getPlayer().getDetails().getSession().write(buffer);
    }

}
