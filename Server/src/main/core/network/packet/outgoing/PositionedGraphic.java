package core.network.packet.outgoing;

import core.game.world.map.Location;
import core.game.world.update.flag.context.Graphic;
import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.PositionedGraphicContext;

/**
 * The positioned graphic outgoing packet.
 * @author Emperor
 */
public final class PositionedGraphic implements OutgoingPacket<PositionedGraphicContext> {

	@Override
	public void send(PositionedGraphicContext context) {
		Location l = context.getLocation();
		Graphic g = context.getGraphic();
		int offsetHash = (context.offsetX << 4) | context.offsetY;
		IoBuffer buffer = new IoBuffer()
				.put(26)					//update current scene x and scene y client-side
				.putC(context.sceneX)  		//this has to be done for each graphic being sent
				.put(context.sceneY)        //opcode 26 is the lastSceneX/lastSceneY update packet
				.put(17).put(offsetHash)   	//send the graphics
				.putShort(g.getId())
				.put(g.getHeight())
				.putShort(g.getDelay());
		context.getPlayer().getSession().write(buffer);
	}

}
