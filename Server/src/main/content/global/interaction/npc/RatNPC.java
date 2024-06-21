package content.global.interaction.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Rat npc.
 */
@Initializable
public class RatNPC extends AbstractNPC {
    private static final int[] ID = {47, 2682, 2980, 2981, 3007, 3008, 3009, 3010, 3011, 3012, 3013, 3014, 3015, 3016, 3017, 3018, 4396, 4415, 7202, 7204, 7417, 7461};
    private static final Item RAT_TAIL = new Item(300);

    /**
     * Instantiates a new Rat npc.
     */
    public RatNPC() {
        super(0, null);
    }

    private RatNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new RatNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (killer instanceof Player) {
            final Player p = ((Player) killer);
            if (p.getQuestRepository().getQuest("Witch's Potion").isStarted(p)) {
                GroundItemManager.create(RAT_TAIL, getLocation(), p);
            }
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
