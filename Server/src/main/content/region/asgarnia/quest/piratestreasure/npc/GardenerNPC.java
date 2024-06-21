package content.region.asgarnia.quest.piratestreasure.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Gardener npc.
 */
@Initializable
public final class GardenerNPC extends AbstractNPC {

    private static final int[] ID = {1217};

    /**
     * Instantiates a new Gardener npc.
     */
    public GardenerNPC() {
        super(0, null, true);
    }

    private GardenerNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new GardenerNPC(id, location);
    }

    @Override
    public void tick() {
        super.tick();
        if (getAttribute("target", null) != null) {
            final Player target = getAttribute("target", null);
            if (getProperties().getCombatPulse().isRunning()) {
                getProperties().getCombatPulse().attack(target);
            }
            if (!target.isActive() || target.getLocation().getDistance(getLocation()) > 16) {
                target.getSavedData().getQuestData().setGardenerAttack(true);
                GameWorld.getPulser().submit(new Pulse(2) {
                    @Override
                    public boolean pulse() {
                        clear();
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public boolean isAttackable(Entity entity, CombatStyle style, boolean message) {
        final Player target = getAttribute("target", null);
        if (target != entity) {
            return false;
        }
        return super.isAttackable(entity, style, message);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        final Player target = getAttribute("target", null);
        if (target != null && target == killer) {
            target.getSavedData().getQuestData().setGardenerAttack(true);
        }
        clear();
        super.finalizeDeath(killer);
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}