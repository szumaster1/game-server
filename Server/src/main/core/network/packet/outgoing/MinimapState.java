package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.MinimapStateContext;

/**
 * Handles the sending of the minimap state outgoing packet.
 * @author Emperor
 */
public final class MinimapState implements OutgoingPacket<MinimapStateContext> {

    @Override
    public void send(MinimapStateContext context) {
        IoBuffer buffer = new IoBuffer(192).put(context.getState());
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getDetails().getSession().write(buffer);
    }

}
