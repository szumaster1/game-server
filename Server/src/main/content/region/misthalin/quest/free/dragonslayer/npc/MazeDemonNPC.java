package content.region.misthalin.quest.free.dragonslayer.npc;

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.world.map.Location;

/**
 * The Maze demon npc.
 */
public final class MazeDemonNPC extends AbstractNPC {

    private static final int[] ID = {82};

    private final static Location LOCATION = Location.create(2936, 9652, 0);

    /**
     * Instantiates a new Maze demon npc.
     */
    public MazeDemonNPC() {
        super(0, null);
    }

    private MazeDemonNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new MazeDemonNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (killer.getLocation().withinDistance(LOCATION)) {
            if (killer instanceof Player) {
                GroundItemManager.create(DragonSlayer.GREEN_KEY, getLocation(), ((Player) killer));
            }
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
