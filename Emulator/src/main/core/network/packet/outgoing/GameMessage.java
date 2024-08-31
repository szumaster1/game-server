package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.PacketHeader;
import core.network.packet.context.GameMessageContext;

/**
 * Game message.
 * @author Emperor
 */
public class GameMessage implements OutgoingPacket<GameMessageContext> {

    @Override
    public void send(GameMessageContext context) {
        IoBuffer buffer = new IoBuffer(70, PacketHeader.BYTE);
        buffer.putString(context.getMessage());
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getSession().write(buffer);
    }
}
