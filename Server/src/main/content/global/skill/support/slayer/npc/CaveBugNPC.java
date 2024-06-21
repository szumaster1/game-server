package content.global.skill.support.slayer.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Cave bug npc.
 */
@Initializable
public class CaveBugNPC extends AbstractNPC {


//	private static final ZoneBorders CAVE_BORDER = new ZoneBorders(3139, 9534, 3260, 9587);

    /**
     * Instantiates a new Cave bug npc.
     */
    public CaveBugNPC() {
        super(-1, null);
    }

    /**
     * Instantiates a new Cave bug npc.
     *
     * @param id       the id
     * @param location the location
     */
    public CaveBugNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public void finalizeDeath(Entity killer) {
        super.finalizeDeath(killer);
        if (killer instanceof Player) {
            Player p = killer.asPlayer();
        }
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new CaveBugNPC(id, location);
    }

    @Override
    public int[] getIds() {
        return new int[]{1832, 1833, 5750};
    }

}
