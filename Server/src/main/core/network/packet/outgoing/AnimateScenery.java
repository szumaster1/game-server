package core.network.packet.outgoing;

import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.AnimateObjectContext;

/**
 * Represents the packet used to animate an object.
 * @author Vexia
 * @date 10/11/2013
 */
public class AnimateScenery implements OutgoingPacket<AnimateObjectContext> {

	/**
	 * Writes the packet.
	 * @param buffer The buffer.
	 */
	public static IoBuffer write(IoBuffer buffer, Animation animation) {
		Scenery object = animation.getObject();
		Location l = object.getLocation();
		buffer.put(20);
		buffer.putS((l.getChunkOffsetX() << 4) | (l.getChunkOffsetY() & 0x7));
		buffer.putS((object.getType() << 2) + (object.getRotation() & 0x3));
		buffer.putLEShort(animation.getId());
		return buffer;
	}

	@Override
	public void send(AnimateObjectContext context) {
		Player player = context.getPlayer();
		Scenery object = context.getAnimation().getObject();
		IoBuffer buffer = write(UpdateAreaPosition.getBuffer(player, object.getLocation().getChunkBase()), context.getAnimation());
		buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());player.getSession().write(buffer);
	}
}
