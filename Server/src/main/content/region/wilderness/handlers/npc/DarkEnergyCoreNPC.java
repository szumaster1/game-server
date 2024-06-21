package content.region.wilderness.handlers.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.ImpactHandler.HitsplatType;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.utilities.RandomFunction;

import static core.api.ContentAPIKt.isPoisoned;
import static core.api.ContentAPIKt.isStunned;

/**
 * The Dark energy core npc.
 */
@Initializable
public final class DarkEnergyCoreNPC extends AbstractNPC {

    private NPC master;

    private int ticks = 0;

    private int fails = 0;

    /**
     * Instantiates a new Dark energy core npc.
     */
    public DarkEnergyCoreNPC() {
        this(8127, null);
    }

    /**
     * Instantiates a new Dark energy core npc.
     *
     * @param id       the id
     * @param location the location
     */
    public DarkEnergyCoreNPC(int id, Location location) {
        super(id, location, false);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        DarkEnergyCoreNPC core = new DarkEnergyCoreNPC(id, location);
        if (objects.length > 0) {
            core.master = (NPC) objects[0];
        }
        return core;
    }

    @Override
    public boolean canStartCombat(Entity victim) {
        return false; //No combat needed.
    }

    @Override
    public void handleTickActions() {
        ticks++;
        boolean poisoned = isPoisoned(this);
        if (isStunned(this) || isInvisible()) {
            return;
        }
        if (fails == 0 && poisoned && (ticks % 100) != 0) {
            return;
        }
        if (ticks % 2 == 0) {
            boolean jump = true;
            for (Player p : getViewport().getCurrentPlane().getPlayers()) {
                if (p.getLocation().withinDistance(getLocation(), 1)) {
                    jump = false;
                    int hit = 5 + RandomFunction.random(6);
                    p.getImpactHandler().manualHit(master, hit, HitsplatType.NORMAL);
                    master.getSkills().heal(hit);
                    p.getPacketDispatch().sendMessage("The dark core creature steals some life from you for its master.");
                }
            }
            if (jump) {
                Entity victim = master.getProperties().getCombatPulse().getVictim();
                if (++fails >= 3 && victim != null && victim.getViewport().getCurrentPlane() == getViewport().getCurrentPlane()) {
                    jump(victim.getLocation());
                    fails = 0;
                }
            } else {
                fails = 0;
            }
        }
    }

    private void jump(final Location location) {
        setInvisible(true);
        Projectile.create(getLocation(), location, 1828, 0, 0, 0, 60, 20, 0).send();
        GameWorld.getPulser().submit(new Pulse(2, this) {
            @Override
            public boolean pulse() {
                getProperties().setTeleportLocation(location);
                setInvisible(false);
                return true;
            }
        });
    }

    @Override
    public int[] getIds() {
        return new int[]{8127};
    }

}
