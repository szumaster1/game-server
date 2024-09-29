package content.region.misthalin.varrock.quest.priest.handlers;

import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.quest.Quest;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.plugin.Initializable;
import org.rs.consts.QuestName;

/**
 * Represents the Monk of Zamorak NPC.
 */
@Initializable
public final class MonkOfZamorakNPC extends AbstractNPC {

    private static final int[] ID = {1046};
    private static final Item GOLDEN_KEY = new Item(2944, 1);

    /**
     * Instantiates a new Monk of Zamorak NPC.
     */
    public MonkOfZamorakNPC() {
        super(0, null);
    }

    private MonkOfZamorakNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new MonkOfZamorakNPC(id, location);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        final Player p = ((Player) killer);
        final Quest quest = p.getQuestRepository().getQuest(QuestName.PRIEST_IN_PERIL);
        if (quest.isStarted(p)) {
            GroundItemManager.create(GOLDEN_KEY, getLocation(), p);
        }
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
