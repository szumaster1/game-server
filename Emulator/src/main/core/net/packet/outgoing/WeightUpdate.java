package core.net.packet.outgoing;

import core.net.packet.IoBuffer;
import core.net.packet.OutgoingPacket;
import core.net.packet.context.PlayerContext;

/**
 * Handles the outgoing weight update packet.
 * @author Emperor
 */
public final class WeightUpdate implements OutgoingPacket<PlayerContext> {

    @Override
    public void send(PlayerContext context) {
        IoBuffer buffer = new IoBuffer(174);
        buffer.putShort((int) context.getPlayer().getSettings().getWeight());
        //    context.getPlayer().getSession().write(buffer);
    }

}
