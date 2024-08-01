package core.network.packet.outgoing;

import core.network.packet.OutgoingPacket;
import core.network.packet.context.PlayerContext;

public final class PingPacket implements OutgoingPacket<PlayerContext> {

    @Override
    public void send(PlayerContext context) {
        // TODO: Find real ping packet, this is actually clear minimap flag
        // packet.
        // context.getPlayer().getDetails().getSession().write(new
        // IoBuffer(47));
    }

}
