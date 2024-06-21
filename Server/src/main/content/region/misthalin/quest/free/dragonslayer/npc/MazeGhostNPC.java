package content.region.misthalin.quest.free.dragonslayer.npc;

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.world.map.Location;
import core.utilities.RandomFunction;

/**
 * The Maze ghost npc.
 */
public final class MazeGhostNPC extends AbstractNPC {

    private static final int[] ID = {103};

    private static final Location LOCATION = Location.create(2926, 3253, 1);

    /**
     * Instantiates a new Maze ghost npc.
     */
    public MazeGhostNPC() {
        super(0, null);
    }

    private MazeGhostNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new MazeGhostNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (killer.getLocation().withinDistance(LOCATION)) {
            if (killer instanceof Player) {
                if (RandomFunction.random(0, 4) == 2) {
                    GroundItemManager.create(DragonSlayer.ORANGE_KEY, getLocation(), ((Player) killer));
                }
            }
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
