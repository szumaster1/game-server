package content.global.skill.support.slayer.npc;

import content.global.skill.support.slayer.SlayerEquipmentFlags;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.*;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.tools.RandomFunction;

/**
 * The Cave horror npc.
 */
@Initializable
public final class CaveHorrorNPC extends AbstractNPC {
    private static final MeleeSwingHandler COMBAT_HANDLER = new MeleeSwingHandler() {
        @Override
        public void impact(Entity entity, Entity victim, BattleState state) {
            if (victim instanceof Player) {
                final Player player = (Player) victim;
                if (!hasWitchwood(player)) {
                    if (RandomFunction.random(10) < 5) {
                        state.setEstimatedHit(player.getSkills().getLifepoints() / 10);
                    }
                }
            }
            super.impact(entity, victim, state);
        }

        @Override
        public InteractionType isAttackable(Entity entity, Entity victim) {
            return CombatStyle.MELEE.getSwingHandler().isAttackable(entity, victim);
        }
    };

    /**
     * Instantiates a new Cave horror npc.
     *
     * @param id       the id
     * @param location the location
     */
    public CaveHorrorNPC(int id, Location location) {
        super(id, location);
    }

    /**
     * Instantiates a new Cave horror npc.
     */
    public CaveHorrorNPC() {
        super(0, null);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new CaveHorrorNPC(id, location);
    }

    @Override
    public CombatSwingHandler getSwingHandler(boolean swing) {
        return COMBAT_HANDLER;
    }

    /**
     * Has witchwood boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public static boolean hasWitchwood(Player player) {
        return SlayerEquipmentFlags.hasWitchwoodIcon(player);
    }

    @Override
    public int[] getIds() {
        return new int[]{4354, 4355, 4353, 4356, 4357};
    }

}
