package core.net.packet.outgoing;

import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.net.packet.IoBuffer;
import core.net.packet.OutgoingPacket;
import core.net.packet.context.BuildSceneryContext;

/**
 * Clear scenery.
 * @author Emperor
 */
public final class ClearScenery implements OutgoingPacket<BuildSceneryContext> {

    /**
     * Write io buffer.
     *
     * @param buffer the buffer
     * @param object the object
     * @return the io buffer
     */
    public static IoBuffer write(IoBuffer buffer, Scenery object) {
        Location l = object.getLocation();
        /*
         * Opcode
         */
        buffer.put(195).putC((object.getType() << 2) + (object.getRotation() & 3)).put((l.getChunkOffsetX() << 4) | (l.getChunkOffsetY() & 0x7));
        return buffer;
    }

    @Override
    public void send(BuildSceneryContext context) {
        Player player = context.getPlayer();
        Scenery o = context.getScenery();
        IoBuffer buffer = write(UpdateAreaPosition.getBuffer(player, o.getLocation().getChunkBase()), o);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().output);
        player.getSession().write(buffer);

    }
}
