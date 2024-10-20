package core.net.packet.outgoing;

import core.net.packet.IoBuffer;
import core.net.packet.OutgoingPacket;
import core.net.packet.context.WindowsPaneContext;

/**
 * Handles the windows pane outgoing packet.
 * @author Emperor
 */
public final class WindowsPane implements OutgoingPacket<WindowsPaneContext> {

    @Override
    public void send(WindowsPaneContext context) {
        IoBuffer buffer = new IoBuffer(145);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().output);
        buffer.putLEShortA(context.windowId);
        buffer.putS(context.type);
        buffer.putLEShortA(context.getPlayer().getInterfaceManager().getPacketCount(1));
        context.getPlayer().getDetails().getSession().write(buffer);
    }

}
