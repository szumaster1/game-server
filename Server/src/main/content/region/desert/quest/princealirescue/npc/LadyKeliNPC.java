package content.region.desert.quest.princealirescue.npc;

import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.GameWorld;
import core.game.world.map.Location;

/**
 * The Lady keli npc.
 */
public final class LadyKeliNPC extends AbstractNPC {

    private static final int[] ID = {919};

    /**
     * Instantiates a new Lady keli npc.
     */
    public LadyKeliNPC() {
        super(0, null, true);
    }

    private LadyKeliNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new LadyKeliNPC(id, location);
    }

    @Override
    public boolean isHidden(final Player player) {
        return player.getAttribute("keli-gone", 0) > GameWorld.getTicks();
    }

    @Override
    public int[] getIds() {
        return ID;
    }

    @Override
    public int getWalkRadius() {
        return 2;
    }

}
