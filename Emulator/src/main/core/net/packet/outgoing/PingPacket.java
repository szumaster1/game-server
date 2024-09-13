package core.net.packet.outgoing;

import core.net.packet.OutgoingPacket;
import core.net.packet.context.PlayerContext;

/**
 * Handles the ping packet sending.
 * @author Emperor
 */
public final class PingPacket implements OutgoingPacket<PlayerContext> {

    @Override
    public void send(PlayerContext context) {
    }

}
