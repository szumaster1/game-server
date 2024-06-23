package content.global.handlers.npc;

import core.game.node.entity.npc.AbstractNPC;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.utilities.RandomFunction;

/**
 * The Duck npc.
 */
@Initializable
public final class DuckNPC extends AbstractNPC {

    private static final String FORCE_CHAT = "Quack!";

    /**
     * Instantiates a new Duck npc.
     */
    public DuckNPC() {
        super(0, null);
    }

    /**
     * Instantiates a new Duck npc.
     *
     * @param id       the id
     * @param location the location
     */
    public DuckNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public void tick() {
        if (RandomFunction.random(45) == 5) {
            sendChat(FORCE_CHAT);
        }
        super.tick();
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new DuckNPC(id, location);
    }

    @Override
    public int[] getIds() {
        return new int[]{46, 2693, 6113};
    }

}
