package content.region.misthalin.quest.member.gertrudescat.npc;

import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Fluff npc.
 */
@Initializable
public final class FluffNPC extends AbstractNPC {
    private static final int[] ID = {2997};

    /**
     * Instantiates a new Fluff npc.
     */
    public FluffNPC() {
        super(0, null);
    }

    private FluffNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new FluffNPC(id, location);
    }

    @Override
    public boolean isHidden(final Player player) {
        if (player.getQuestRepository().getQuest("Gertrude's Cat").getStage(player) < 20) {
            return true;
        }
        return player.getAttribute("hidefluff", 0L) > System.currentTimeMillis();
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
