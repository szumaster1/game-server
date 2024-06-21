package content.region.misthalin.quest.free.dragonslayer.npc;

import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;

/**
 * The Ds ned npc.
 */
public final class DSNedNPC extends AbstractNPC {

    private static final int[] ID = {918};

    /**
     * Instantiates a new Ds ned npc.
     */
    public DSNedNPC() {
        super(0, null);
    }

    private DSNedNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new DSNedNPC(id, location);
    }

    @Override
    public boolean isHidden(final Player player) {
        return player.getQuestRepository().getQuest("Dragon Slayer").getStage(player) != 30 && player.getQuestRepository().getQuest("Dragon Slayer").getStage(player) != 40;
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
