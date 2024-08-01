package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.WindowsPaneContext;

public final class WindowsPane implements OutgoingPacket<WindowsPaneContext> {

    @Override
    public void send(WindowsPaneContext context) {
        IoBuffer buffer = new IoBuffer(145);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        buffer.putLEShortA(context.windowId);
        buffer.putS(context.type);
        buffer.putLEShortA(context.getPlayer().getInterfaceManager().getPacketCount(1));
        context.getPlayer().getDetails().getSession().write(buffer);
    }

}
