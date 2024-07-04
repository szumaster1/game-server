package content.region.misthalin.quest.free.dragonslayer.npc;

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.quest.Quest;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.tools.RandomFunction;

/**
 * The Zombie rat npc.
 */
public final class ZombieRatNPC extends AbstractNPC {

    private static final int[] ID = {6088, 6089, 6090};

    private static final Item RAT_TAIL = new Item(300, 1);

    /**
     * Instantiates a new Zombie rat npc.
     */
    public ZombieRatNPC() {
        super(0, null);
    }

    private ZombieRatNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new ZombieRatNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (killer instanceof Player) {
            final Player p = ((Player) killer);
            Quest quest = p.getQuestRepository().getQuest("Dragon Slayer");
            if (RandomFunction.random(0, 4) == 2) {
                GroundItemManager.create(DragonSlayer.RED_KEY, getLocation(), ((Player) killer));
            }
            quest = p.getQuestRepository().getQuest("Witch's Potion");
            if (quest.getStage(p) > 0 && quest.getStage(p) < 100) {
                GroundItemManager.create(RAT_TAIL, getLocation(), p);
            }
            GroundItemManager.create(new Item(526), getLocation(), p);
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
