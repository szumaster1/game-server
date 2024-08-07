package core.network.packet.outgoing;

import core.game.node.entity.player.Player;
import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.VarcUpdateContext;

/**
 * Varc update.
 */
public class VarcUpdate implements OutgoingPacket<VarcUpdateContext> {
    @Override
    public void send(VarcUpdateContext varcUpdateContext) {
        Player player = varcUpdateContext.getPlayer();
        if (varcUpdateContext.value <= 255) {
            IoBuffer buffer = new IoBuffer(65);
            buffer.putLEShort(player.getInterfaceManager().getPacketCount(1));
            buffer.putC((byte) varcUpdateContext.value);
            buffer.putLEShortA(varcUpdateContext.varcId);
            player.getSession().write(buffer);
        } else {
            IoBuffer buffer = new IoBuffer(69);
            buffer.putLEShortA(player.getInterfaceManager().getPacketCount(1));
            buffer.putInt(varcUpdateContext.value);
            buffer.putShortA(varcUpdateContext.varcId);
            player.getSession().write(buffer);
        }
    }
}
