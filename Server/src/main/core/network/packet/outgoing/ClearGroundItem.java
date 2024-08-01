package core.network.packet.outgoing;

import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.BuildItemContext;

public final class ClearGroundItem implements OutgoingPacket<BuildItemContext> {

    public static IoBuffer write(IoBuffer buffer, Item item) {
        Location l = item.getLocation();
        buffer.put(240);
        buffer.putS((l.getChunkOffsetX() << 4) | (l.getChunkOffsetY() & 0x7)).putShort(item.getId());
        return buffer;
    }

    @Override
    public void send(BuildItemContext context) {
        Player player = context.getPlayer();
        Item item = context.getItem();
        IoBuffer buffer = write(UpdateAreaPosition.getBuffer(player, item.getLocation().getChunkBase()), item);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        player.getSession().write(buffer);
    }
}
