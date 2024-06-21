package content.region.misc.handlers.tutorial;

import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Rat tutorial npc.
 */
@Initializable
public class RatTutorialNPC extends AbstractNPC {

    private static final int[] ID = {86};


    /**
     * Instantiates a new Rat tutorial npc.
     */
    public RatTutorialNPC() {
        super(0, null);
        this.setAggressive(false);
    }


    private RatTutorialNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public void init() {
        super.init();
        setAggressive(false);
        getSkills().setLevel(Skills.HITPOINTS, 5);
        getSkills().setStaticLevel(Skills.HITPOINTS, 5);
        getSkills().setLifepoints(5);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new RatTutorialNPC(id, location);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (!(killer instanceof Player)) {
            return;
        }
        final Player p = ((Player) killer);
        if (killer instanceof Player) {
            if (p.getQuestRepository().getQuest("Witch's Potion").isStarted(p)) {
                GroundItemManager.create(new Item(300), getLocation(), p);
            }
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
