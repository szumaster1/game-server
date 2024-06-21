package content.region.misthalin.quest.free.learningtheropes;

import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.game.world.map.build.DynamicRegion;
import core.game.world.map.zone.MapZone;
import core.game.world.update.flag.context.Animation;

/**
 * The Cellar map zone.
 */
public class CellarMapZone extends MapZone {

    private static final NPC[] NPCS = new NPC[]{NPC.create(7938, Location.create(2524, 5005, 0), Direction.NORTH), NPC.create(7965, Location.create(2524, 4997, 0), Direction.NORTH),};

    /**
     * The constant INSTANCE.
     */
    public static final CellarMapZone INSTANCE = new CellarMapZone();
    private DynamicRegion region;
    private Location base;

    /**
     * Instantiates a new Cellar map zone.
     */
    public CellarMapZone() {
        super("Cellar Map Zone", true);
    }

    @Override
    public void configure() {
        region = DynamicRegion.create(10062);
        setRegionBase();
        registerRegion(region.getId());
        setNpcs();
    }

    /**
     * Create.
     *
     * @param player the player
     */
    public void create(Player player) {
        configure();
        player.teleport(getBase().transform(28, 12, 0));
    }

    private void setNpcs() {
        for (NPC n : NPCS) {
            n = NPC.create(n.getId(), n.getLocation(), n.getDirection());
            n.setLocation(base.transform(n.getLocation().getLocalX(), n.getLocation().getLocalY(), 0));
            n.setRespawn(false);
            n.init();
            n.setWalks(false);
            n.animate(new Animation(-1));
        }
    }

    /**
     * Sets the region base.
     */
    private void setRegionBase() {
        if (region != null) {
            setBase(Location.create(region.getBorders().getSouthWestX(), region.getBorders().getSouthWestY(), 0));
        }
    }

    /**
     * Gets the base.
     *
     * @return the base
     */
    public Location getBase() {
        return base;
    }

    /**
     * Sets the base.
     *
     * @param base the base to set.
     */
    public void setBase(Location base) {
        this.base = base;
    }

    /**
     * Get cellar map zone.
     *
     * @return the cellar map zone
     */
    public static CellarMapZone get() {
        return INSTANCE;
    }
}
