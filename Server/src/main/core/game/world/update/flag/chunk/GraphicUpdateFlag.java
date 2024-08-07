package core.game.world.update.flag.chunk;

import core.game.world.map.Location;
import core.game.world.update.flag.UpdateFlag;
import core.game.world.update.flag.context.Graphic;
import core.network.packet.IoBuffer;

/**
 * Graphic update flag.
 */
public final class GraphicUpdateFlag extends UpdateFlag<Graphic> {

    private final Location location;

    /**
     * Instantiates a new Graphic update flag.
     *
     * @param graphic  the graphic
     * @param location the location
     */
    public GraphicUpdateFlag(Graphic graphic, Location location) {
        super(graphic);
        this.location = location;
    }

    @Override
    public void write(IoBuffer buffer) {
        buffer.put((byte) 17); // opcode
        buffer.put((location.getChunkOffsetX() << 4) | (location.getChunkOffsetY() & 0x7));
        buffer.putShort(context.getId());
        buffer.put(context.getHeight());
        buffer.putShort(context.getDelay());
    }

    @Override
    public int data() {
        return 0;
    }

    @Override
    public int ordinal() {
        return 3;
    }

}
