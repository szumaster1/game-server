package content.region.misthalin.quest.free.romeoandjuliet.npc;

import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Juliet npc.
 */
@Initializable
public final class JulietNPC extends AbstractNPC {

    private static final int[] ID = {637};

    /**
     * Instantiates a new Juliet npc.
     */
    public JulietNPC() {
        super(0, null);
    }

    private JulietNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new JulietNPC(id, location);
    }

    @Override
    public boolean isHidden(final Player player) {
        return player.getQuestRepository().getQuest("Romeo & Juliet").getStage(player) > 60 && player.getQuestRepository().getQuest("Romeo & Juliet").getStage(player) < 100;
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
