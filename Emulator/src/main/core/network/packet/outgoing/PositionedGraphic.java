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

    /*
     * update current scene x and scene y client-side this
     * has to be done for each graphic being sent opcode 26
     * is the lastSceneX/lastSceneY update packet send the graphics
     */
    @Override
    public void send(PositionedGraphicContext context) {
        Location l = context.location;
        Graphic g = context.graphic;
        int offsetHash = (context.offsetX << 4) | context.offsetY;
        IoBuffer buffer = new IoBuffer()
            .put(26)
            .putC(context.sceneX)
            .put(context.sceneY)
            .put(17).put(offsetHash)
            .putShort(g.getId())
            .put(g.getHeight())
            .putShort(g.getDelay());
        context.getPlayer().getSession().write(buffer);
    }

}
