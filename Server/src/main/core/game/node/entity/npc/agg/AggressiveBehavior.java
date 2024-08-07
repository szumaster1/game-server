package core.game.node.entity.npc.agg;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.DeathTask;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.world.map.RegionManager;
import core.game.world.map.zone.impl.WildernessZone;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggressive behavior.
 */
public class AggressiveBehavior {

    /**
     * The constant DEFAULT.
     */
    public static final AggressiveBehavior DEFAULT = new AggressiveBehavior();

    /**
     * The constant WILDERNESS.
     */
    public static final AggressiveBehavior WILDERNESS = new AggressiveBehavior() {

        @Override
        public boolean canSelectTarget(Entity entity, Entity target) {
            if (target.isInvisible()) {
                return false;
            }
            if (!target.isActive() || DeathTask.isDead(target)) {
                return false;
            }
            if (!entity.canSelectTarget(target)) {
                return false;
            }
            if (!target.getProperties().isMultiZone() && target.inCombat()) {
                return false;
            }
            if (target instanceof Player) {
                return ((Player) target).getSkullManager().isWilderness();
            }
            return true;
        }
    };

    /**
     * Instantiates a new Aggressive behavior.
     */
    public AggressiveBehavior() {
        /*
         * empty.
         */
    }

    /**
     * Can select target boolean.
     *
     * @param entity the entity
     * @param target the target
     * @return the boolean
     */
    public boolean canSelectTarget(Entity entity, Entity target) {
        int regionId = target.getLocation().getRegionId();
        if (!target.isActive() || DeathTask.isDead(target)) {
            return false;
        }
        if (!target.getProperties().isMultiZone() && target.inCombat()) {
            return false;
        }
        if (entity instanceof NPC && target instanceof Player) {
            NPC npc = (NPC) entity;
            if (npc.getAggressiveHandler() != null && npc.getAggressiveHandler().isAllowTolerance() && !WildernessZone.isInZone(npc)) {
                if (RegionManager.forId(regionId).isTolerated(target.asPlayer())) {
                    return false;
                }
            }
        }
        int level = target.getProperties().getCurrentCombatLevel();
        if (level > entity.getProperties().getCurrentCombatLevel() << 1 && !ignoreCombatLevelDifference()) {
            return false;
        }
        return true;
    }

    /**
     * Ignore combat level difference boolean.
     *
     * @return the boolean
     */
    public boolean ignoreCombatLevelDifference() {
        return false;
    }

    /**
     * Gets priority flag.
     *
     * @param target the target
     * @return the priority flag
     */
    public int getPriorityFlag(Entity target) {
        int flag = 0;
        if (target.inCombat()) {
            flag++;
        }
        if (target.getLocks().isInteractionLocked()) {
            flag++;
        }
        Entity e = target.getAttribute("aggressor");
        if (e != null && e.getProperties().getCombatPulse().getVictim() == target) {
            flag++;
        }
        return flag;
    }

    /**
     * Gets possible targets.
     *
     * @param entity the entity
     * @param radius the radius
     * @return the possible targets
     */
    public List<Entity> getPossibleTargets(Entity entity, int radius) {
        List<Entity> targets = new ArrayList<>(20);
        for (Player player : RegionManager.getLocalPlayers(entity, radius)) {
            if (canSelectTarget(entity, player)) {
                targets.add(player);
            }
        }
        return targets;
    }

    /**
     * Gets logical target.
     *
     * @param entity          the entity
     * @param possibleTargets the possible targets
     * @return the logical target
     */
    public Entity getLogicalTarget(Entity entity, List<Entity> possibleTargets) {
        Entity target = null;
        int comparingFlag = Integer.MAX_VALUE;
        for (Entity e : possibleTargets) {
            int flag = getPriorityFlag(e);
            if (flag <= comparingFlag) {
                comparingFlag = flag;
                target = e;
            }
        }
        return target;
    }
}
