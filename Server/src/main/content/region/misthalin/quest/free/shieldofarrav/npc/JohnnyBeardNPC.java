package content.region.misthalin.quest.free.shieldofarrav.npc;

import content.region.misthalin.quest.free.shieldofarrav.ShieldofArrav;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.quest.Quest;
import core.game.node.item.GroundItemManager;
import core.game.world.map.Location;

/**
 * The Johnny beard npc.
 */
public final class JohnnyBeardNPC extends AbstractNPC {

    private static final int[] ID = {645};

    /**
     * Instantiates a new Johnny beard npc.
     */
    public JohnnyBeardNPC() {
        super(0, null);
    }

    private JohnnyBeardNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new JohnnyBeardNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        final Player p = ((Player) killer);
        final Quest quest = p.getQuestRepository().getQuest("Shield of Arrav");
        if (quest.getStage(p) == 60 && ShieldofArrav.isPhoenixMission(p) && !p.getInventory().containsItem(ShieldofArrav.INTEL_REPORT) && !p.getBank().containsItem(ShieldofArrav.INTEL_REPORT)) {
            GroundItemManager.create(ShieldofArrav.INTEL_REPORT, getLocation(), p);
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
