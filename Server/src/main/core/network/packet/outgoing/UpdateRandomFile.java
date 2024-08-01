package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.PlayerContext;

public final class UpdateRandomFile implements OutgoingPacket<PlayerContext> {

    @Override
    public void send(PlayerContext context) {
        IoBuffer buffer = new IoBuffer(211);
        buffer.putInt(1); // Let's assume this is UID.
        buffer.put(0);
        buffer.put(0);
        buffer.put(0);
        buffer.put(0);
        for (int i = 0; i < 4; i++) {
            buffer.putInt(i + 100);
        }
        // TODO context.getPlayer().getSession().write(buffer);
    }

}
