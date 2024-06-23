package content.global.handlers.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatSwingHandler;
import core.game.node.entity.combat.MeleeSwingHandler;
import core.game.node.entity.npc.AbstractNPC;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.utilities.RandomFunction;

/**
 * The Monk npc.
 */
@Initializable
public final class MonkNPC extends AbstractNPC {
    private static final CombatAction COMBAT = new CombatAction();

    /**
     * Instantiates a new Monk npc.
     */
    public MonkNPC() {
        this(7727, null);
    }

    /**
     * Instantiates a new Monk npc.
     *
     * @param id       the id
     * @param location the location
     */
    public MonkNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new MonkNPC(id, location);
    }

    @Override
    public CombatSwingHandler getSwingHandler(boolean swing) {
        return COMBAT;
    }

    @Override
    public int[] getIds() {
        return new int[]{7727};
    }

    private static class CombatAction extends MeleeSwingHandler {

        @Override
        public int swing(Entity entity, Entity victim, BattleState state) {
            if (entity.getSkills().getLifepoints() != entity.getSkills().getMaximumLifepoints() && RandomFunction.randomize(10) < 2) {
                entity.animate(Animation.create(709));
                entity.getSkills().heal(2);
                entity.getProperties().getCombatPulse().setNextAttack(4);
                return -1;
            }
            return super.swing(entity, victim, state);
        }
    }

}
