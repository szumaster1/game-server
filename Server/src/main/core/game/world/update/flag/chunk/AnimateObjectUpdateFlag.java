package core.game.world.update.flag.chunk;

import core.game.world.update.flag.UpdateFlag;
import core.game.world.update.flag.context.Animation;
import core.network.packet.IoBuffer;
import core.network.packet.outgoing.AnimateScenery;

public final class AnimateObjectUpdateFlag extends UpdateFlag<Animation> {

    public AnimateObjectUpdateFlag(Animation context) {
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
