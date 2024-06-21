package content.region.misthalin.quest.free.romeoandjuliet.npc;

import core.game.node.entity.npc.AbstractNPC;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Romeo npc.
 */
@Initializable
public class RomeoNPC extends AbstractNPC {

    private static final int[] ID = {639};

    private static int speakDelay;

    /**
     * Instantiates a new Romeo npc.
     */
    public RomeoNPC() {
        super(0, null, true);
    }

    private RomeoNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new RomeoNPC(id, location);
    }

    @Override
    public void init() {
        setWalks(true);
        super.init();
        setWalks(true);
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
