package content.region.misthalin.quest.free.dragonslayer.npc;

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.world.map.Location;
import core.tools.RandomFunction;

/**
 * The Maze skeleton npc.
 */
public final class MazeSkeletonNPC extends AbstractNPC {

    private static final int[] ID = {90};

    private static final Location LOCATION = Location.create(2927, 3253, 2);

    /**
     * Instantiates a new Maze skeleton npc.
     */
    public MazeSkeletonNPC() {
        super(0, null);
    }

    private MazeSkeletonNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new MazeSkeletonNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (killer.getLocation().withinDistance(LOCATION)) {
            if (killer instanceof Player) {
                if (RandomFunction.random(0, 5) == 2) {
                    GroundItemManager.create(DragonSlayer.YELLOW_KEY, getLocation(), ((Player) killer));
                }
            }
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
