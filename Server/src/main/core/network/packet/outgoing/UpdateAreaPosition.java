package core.network.packet.outgoing;

import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.PacketHeader;
import core.network.packet.context.AreaPositionContext;

public final class UpdateAreaPosition implements OutgoingPacket<AreaPositionContext> {

    public static IoBuffer getChunkUpdateBuffer(Player player, Location base) {
        int x = base.getSceneX(player.getPlayerFlags().getLastSceneGraph());
        int y = base.getSceneY(player.getPlayerFlags().getLastSceneGraph());
        return new IoBuffer(230, PacketHeader.SHORT).putA(y).putS(x);
    }

    public static IoBuffer getBuffer(Player player, Location base) {
        int x = base.getSceneX(player.getPlayerFlags().getLastSceneGraph());
        int y = base.getSceneY(player.getPlayerFlags().getLastSceneGraph());
        return new IoBuffer(26).putC(x).put(y);
    }

    @Override
    public void send(AreaPositionContext context) {
        IoBuffer buffer = getBuffer(context.getPlayer(), context.location);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getSession().write(buffer);
    }

}
