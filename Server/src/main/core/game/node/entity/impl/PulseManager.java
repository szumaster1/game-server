package core.game.node.entity.impl;

import core.game.interaction.MovementPulse;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatPulse;
import core.game.node.entity.combat.DeathTask;
import core.game.node.entity.player.Player;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Pulse manager.
 */
public final class PulseManager {

    private final HashMap<PulseType, Pulse> currentPulses = new HashMap<>();

    /**
     * Run.
     *
     * @param pulse the pulse
     */
    public void run(Pulse pulse) {
        run(pulse, PulseType.STANDARD);
    }

    /**
     * Run.
     *
     * @param pulse     the pulse
     * @param pulseType the pulse type
     */
    public void run(Pulse pulse, PulseType pulseType) {
        ArrayList<PulseType> toRemove = new ArrayList<>(currentPulses.size());
        currentPulses.forEach((key, value) -> {
            if (value != null && !value.isRunning()) {
                toRemove.add(key);
            }
        });
        for (PulseType t : toRemove) currentPulses.remove(t);

        if (currentPulses.get(PulseType.STRONG) != null) {
            //strong pulses cannot be interrupted or ran alongside anything else. They are the ONLY pulse type when they are present.
            return;
        }

        if (!clear(pulseType)) {
            return;
        }

        if (pulseType == PulseType.STRONG) {
            clear();
        }

        currentPulses.put(pulseType, pulse);
        pulse.start();
        if (pulse.isRunning()) {
            GameWorld.getPulser().submit(pulse);
        }
    }

    /**
     * Clear.
     */
    public void clear() {
        currentPulses.forEach((type, pulse) -> {
            if (type != PulseType.STRONG && pulse != null) pulse.stop();
        });
    }

    /**
     * Clear boolean.
     *
     * @param pulseType the pulse type
     * @return the boolean
     */
    public boolean clear(PulseType pulseType) {
        Pulse pulse = currentPulses.get(pulseType);

        if (pulse != null) {
            pulse.stop();
            currentPulses.remove(pulseType);
        }
        return true;
    }

    /**
     * Run unhandled action pulse.
     *
     * @param player    the player
     * @param pulseType the pulse type
     * @return the pulse
     */
    public Pulse runUnhandledAction(final Player player, PulseType pulseType) {
        Pulse pulse = new Pulse(1, player) {
            @Override
            public boolean pulse() {
                player.getPacketDispatch().sendMessage("Nothing interesting happens.");
                return true;
            }
        };
        run(pulse, pulseType);
        return pulse;
    }

    /**
     * Is moving pulse boolean.
     *
     * @return the boolean
     */
    public boolean isMovingPulse() {
        if (!hasPulseRunning()) {
            return false;
        }

        Pulse current = getCurrent();
        return current instanceof MovementPulse || current instanceof CombatPulse;
    }

    /**
     * Has pulse running boolean.
     *
     * @return the boolean
     */
    public boolean hasPulseRunning() {
        return getCurrent() != null && getCurrent().isRunning();
    }

    /**
     * Cancel death task.
     *
     * @param e the e
     */
    public static void cancelDeathTask(Entity e) {
        if (!DeathTask.isDead(e) || e.getPulseManager().getCurrent() == null) {
            return;
        }
        e.getPulseManager().getCurrent().stop();
    }

    /**
     * Gets current.
     *
     * @return the current
     */
    public Pulse getCurrent() {
        PulseType[] types = PulseType.values();
        for (PulseType type : types) {
            if (currentPulses.get(type) != null) {
                return currentPulses.get(type);
            }
        }
        return null;
    }

}

