package core.network.packet.outgoing;

import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.LocationContext;

/**
 * Instanced location update.
 * @author Emperor
 */
public final class InstancedLocationUpdate implements OutgoingPacket<LocationContext> {

    @Override
    public void send(LocationContext context) {
        IoBuffer buffer = new IoBuffer(110);
        Location l = context.location;
        Player player = context.getPlayer();
        int flag = l.getZ() << 1;
        if (context.isTeleport()) {
            flag |= 0x1;
        }
        buffer.putS(flag);
        buffer.put(l.getSceneX(player.getPlayerFlags().getLastSceneGraph()));
        buffer.putA(l.getSceneY(player.getPlayerFlags().getLastSceneGraph()));
        // TODO player.getSession().write(buffer);
    }

}
