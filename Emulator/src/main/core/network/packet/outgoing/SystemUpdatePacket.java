package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.SystemUpdateContext;

/**
 * Handles the system update packet.
 * @author Vexia
 */
public class SystemUpdatePacket implements OutgoingPacket<SystemUpdateContext> {

    @Override
    public void send(final SystemUpdateContext context) {
        IoBuffer buffer = new IoBuffer(85).putShort(context.getTime());
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getDetails().getSession().write(buffer);
    }

}
