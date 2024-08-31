package core.network.packet.outgoing;

import core.game.node.entity.player.Player;
import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.DefaultContext;

/**
 * Interface set angle.
 * @author Emperor
 */
public final class InterfaceSetAngle implements OutgoingPacket<DefaultContext> {
    @Override
    public void send(DefaultContext context) {
        Player player = context.getPlayer();
        Object[] objects = context.getObjects();
        int pitch = (Integer) objects[0];
        int scale = (Integer) objects[1];
        int yaw = (Integer) objects[2];
        int interfaceId = (Integer) objects[3];
        int childId = (Integer) objects[4];
        IoBuffer buffer = new IoBuffer(132);
        buffer.putShort(pitch);
        buffer.putShortA(player.getInterfaceManager().getPacketCount(1));
        buffer.putLEShortA(scale);
        buffer.putLEShortA(yaw);
        buffer.putInt(interfaceId << 16 | childId);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getSession().write(buffer);
    }
}
