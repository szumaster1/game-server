package content.region.misthalin.quest.free.dragonslayer.npc;

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.world.map.Location;
import core.utilities.RandomFunction;

/**
 * The Maze zombie npc.
 */
public final class MazeZombieNPC extends AbstractNPC {

    private static final int[] ID = {75};

    private final static Location LOCATION = Location.create(2933, 9641, 0);

    /**
     * Instantiates a new Maze zombie npc.
     */
    public MazeZombieNPC() {
        super(0, null);
    }

    private MazeZombieNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new MazeZombieNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (killer.getLocation().withinDistance(LOCATION)) {
            if (killer instanceof Player) {
                if (RandomFunction.random(0, 3) == 2) {
                    GroundItemManager.create(DragonSlayer.BLUE_KEY, getLocation(), ((Player) killer));
                }
            }
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
