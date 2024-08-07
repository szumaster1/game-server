package core.network.packet.outgoing;

import core.network.packet.OutgoingPacket;
import core.network.packet.context.PlayerContext;

/**
 * Handles the ping packet sending.
 * @author Emperor
 */
public final class PingPacket implements OutgoingPacket<PlayerContext> {

    @Override
    public void send(PlayerContext context) {
    }

}
