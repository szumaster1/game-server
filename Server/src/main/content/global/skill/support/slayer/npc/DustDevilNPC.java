package content.global.skill.support.slayer.npc;

import content.global.skill.support.slayer.SlayerEquipmentFlags;
import content.global.skill.support.slayer.Tasks;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.*;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Dust devil npc.
 */
@Initializable
public final class DustDevilNPC extends AbstractNPC {

    private static final int[] SKILLS = new int[]{Skills.ATTACK, Skills.STRENGTH, Skills.RANGE, Skills.MAGIC};

    private static final MeleeSwingHandler COMBAT_HANDLER = new MeleeSwingHandler() {
        @Override
        public void impact(Entity entity, Entity victim, BattleState state) {
            if (victim instanceof Player) {
                final Player player = (Player) victim;
                if (!SlayerEquipmentFlags.hasFaceMask(player)) {
                    for (int i : SKILLS) {
                        player.getSkills().updateLevel(i, -player.getSkills().getStaticLevel(i), 0);
                    }
                    player.getSkills().decrementPrayerPoints((double) player.getSkills().getStaticLevel(Skills.PRAYER) / 2);
                    state.setEstimatedHit(14);
                }
            }
            super.impact(entity, victim, state);
        }

        @Override
        public InteractionType isAttackable(Entity entity, Entity victim) {
            return CombatStyle.MAGIC.getSwingHandler().isAttackable(entity, victim);
        }
    };

    /**
     * Instantiates a new Dust devil npc.
     *
     * @param id       the id
     * @param location the location
     */
    public DustDevilNPC(int id, Location location) {
        super(id, location);
        super.getProperties().getCombatPulse().setHandler(COMBAT_HANDLER);
    }

    /**
     * Instantiates a new Dust devil npc.
     */
    public DustDevilNPC() {
        super(0, null);
    }

    @Override
    public CombatSwingHandler getSwingHandler(boolean swing) {
        return COMBAT_HANDLER;
    }

    @Override
    public void checkImpact(BattleState state) {
        super.checkImpact(state);
        if (state.getAttacker() instanceof Player) {
            Player player = (Player) state.getAttacker();
            if (!SlayerEquipmentFlags.hasFaceMask(player)) {
                state.neutralizeHits();
            }
        }
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new DustDevilNPC(id, location);
    }

    @Override
    public int[] getIds() {
        return Tasks.DUST_DEVILS.getNpcs();
    }

}
