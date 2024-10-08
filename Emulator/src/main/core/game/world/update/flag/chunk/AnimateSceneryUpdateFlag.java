package core.game.world.update.flag.chunk;

import core.game.world.update.flag.UpdateFlag;
import core.game.world.update.flag.context.Animation;
import core.net.packet.IoBuffer;
import core.net.packet.outgoing.AnimateScenery;

/**
 * The animate object update flag.
 * @author Emperor
 */
public final class AnimateSceneryUpdateFlag extends UpdateFlag<Animation> {

    /**
     * Constructs a new {@code AnimateSceneryUpdateFlag} {@code Object}.
     *
     * @param context The animation.
     */
    public AnimateSceneryUpdateFlag(Animation context) {
        super(context);
    }

    @Override
    public void write(IoBuffer buffer) {
        AnimateScenery.write(buffer, context);
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