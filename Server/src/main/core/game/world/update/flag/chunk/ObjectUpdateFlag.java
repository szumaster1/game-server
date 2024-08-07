package core.game.world.update.flag.chunk;

import core.game.node.scenery.Scenery;
import core.game.world.update.flag.UpdateFlag;
import core.network.packet.IoBuffer;
import core.network.packet.outgoing.ClearScenery;
import core.network.packet.outgoing.ConstructScenery;

/**
 * Object update flag.
 */
public class ObjectUpdateFlag extends UpdateFlag<Object> {

    private final Scenery object;

    private final boolean remove;

    /**
     * Instantiates a new Object update flag.
     *
     * @param object the object
     * @param remove the remove
     */
    public ObjectUpdateFlag(Scenery object, boolean remove) {
        super(null);
        this.object = object;
        this.remove = remove;
    }

    @Override
    public void write(IoBuffer buffer) {
        if (remove) {
            ClearScenery.write(buffer, object);
        } else {
            ConstructScenery.write(buffer, object);
        }
    }

    @Override
    public int data() {
        return 0;
    }

    @Override
    public int ordinal() {
        return 0;
    }

}
