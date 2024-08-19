package core.game.world.update.flag.chunk;

import core.game.node.scenery.Scenery;
import core.game.world.update.flag.UpdateFlag;
import core.network.packet.IoBuffer;
import core.network.packet.outgoing.ClearScenery;
import core.network.packet.outgoing.ConstructScenery;

/**
 * The object update flag.
 * @author Emperor
 */
public class SceneryUpdateFlag extends UpdateFlag<Object> {

    /**
     * The object to update.
     */
    private final Scenery object;

    /**
     * If we should remove the object.
     */
    private final boolean remove;

    /**
     * Constructs a new {@code ObjectUpdateFlag} {@code Object}.
     * @param object The object to update.
     * @param remove If we should remove the object.
     */
    public SceneryUpdateFlag(Scenery object, boolean remove) {
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