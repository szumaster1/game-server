package content.region.misthalin.quest.free.dragonslayer.npc;

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.world.map.Location;

/**
 * The Wormbrain npc.
 */
public final class WormbrainNPC extends AbstractNPC {

    private static final int[] ID = {745};

    /**
     * Instantiates a new Wormbrain npc.
     */
    public WormbrainNPC() {
        super(0, null);
    }

    private WormbrainNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new WormbrainNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (killer instanceof Player) {
            if (((Player) killer).getQuestRepository().getQuest("Dragon Slayer").getStage(killer.asPlayer()) == 20 && !((Player) killer).getInventory().containsItem(DragonSlayer.WORMBRAIN_PIECE) && !((Player) killer).getBank().containsItem(DragonSlayer.WORMBRAIN_PIECE)) {
                GroundItemManager.create(DragonSlayer.WORMBRAIN_PIECE, getLocation(), ((Player) killer));
                ((Player) killer).getPacketDispatch().sendMessage("Wormbrain drops a map piece on the floor.");
            }
        }
    }

    @Override
    public boolean isAttackable(Entity entity, CombatStyle style, boolean message) {
        if (entity instanceof Player) {
            final Player player = (Player) entity;
            if (player.getQuestRepository().getQuest("Dragon Slayer").getStage(player) != 20) {
                if (message) {
                    player.getPacketDispatch().sendMessage("The goblin is already in prison. You have no reason to attack him.");
                }
                return false;
            }
        }
        return super.isAttackable(entity, style, message);
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
