package core.game.node.entity.player.link.prayer;

import org.rs.consts.Sounds;
import core.game.event.PrayerDeactivatedEvent;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.ImpactHandler.HitsplatType;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillBonus;
import core.game.node.entity.skill.Skills;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.context.Graphic;
import core.tools.RandomFunction;

import java.util.ArrayList;
import java.util.List;

import static core.api.ContentAPIKt.playAudio;
import static core.api.ContentAPIKt.setVarp;

/**
 * Represents a managing class of a players prayers.
 * @author Vexia, Emperor
 */
public final class Prayer {

    /**
     * Represents the list of active prayers.
     */
    private final List<PrayerType> active = new ArrayList<>(20);

    /**
     * Represents the player instance.
     */
    private final Player player;

    private int prayerActiveTicks = 0;

    /**
     * Constructs a new {@code Prayer} {@code Object}.
     *
     * @param player the player
     */
    public Prayer(Player player) {
        this.player = player;
    }

    /**
     * Method used to toggle a prayer.
     *
     * @param type the type of prayer.
     * @return the boolean
     */
    public final boolean toggle(final PrayerType type) {
        if (!permitted(type)) {
            return false;
        }
        return type.toggle(player, !active.contains(type));
    }

    /**
     * Method used to reset this prayer managers cached prayers.
     */
    public void reset() {
        // Immediately clear the lights on the client interface and terminate any bonuses
        for (PrayerType type : getActive()) {
            setVarp(player, type.getConfig(), 0, false);
            player.dispatch(new PrayerDeactivatedEvent(type));
        }
        getActive().clear();
        // Clear the overhead prayer icon a tick later
        GameWorld.getPulser().submit(new Pulse(1) {
            @Override
            public boolean pulse() {
                player.getAppearance().setHeadIcon(-1);
                player.getAppearance().sync();
                return true;
            }
        });
    }

    /**
     * Starts the redemption effect.
     */
    public void startRedemption() {
        playAudio(player, Sounds.REDEMPTION_HEAL_2681);
        player.graphics(Graphic.create(436));
        player.getSkills().heal((int) (player.getSkills().getStaticLevel(Skills.PRAYER) * 0.25));
        player.getSkills().setPrayerPoints(0.0);
        reset();
    }

    /**
     * Starts the retribution effect.
     *
     * @param killer The entity who killed this player.
     */
    public void startRetribution(Entity killer) {
        Location l = player.getLocation();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x != 0 || y != 0) {
                    Projectile.create(l, l.transform(x, y, 0), 438, 0, 0, 10, 20, 0, 11).send();
                }
            }
        }
        playAudio(player, Sounds.FIREBREATH_159);
        player.graphics(Graphic.create(437));
        int maximum = (int) (player.getSkills().getStaticLevel(Skills.PRAYER) * 0.25) - 1;
        if (killer != player && killer.getLocation().withinDistance(player.getLocation(), 1)) {
            killer.getImpactHandler().manualHit(player, 1 + RandomFunction.randomize(maximum), HitsplatType.NORMAL);
        }
        if (player.getProperties().isMultiZone()) {
            @SuppressWarnings("rawtypes")
            List targets = null;
            if (killer instanceof NPC) {
                targets = RegionManager.getSurroundingNPCs(player, player, killer);
            } else {
                targets = RegionManager.getSurroundingPlayers(player, player, killer);
            }
            for (Object o : targets) {
                Entity entity = (Entity) o;
                if (entity.isAttackable(player, CombatStyle.MAGIC, false)) {
                    entity.getImpactHandler().manualHit(player, 1 + RandomFunction.randomize(maximum), HitsplatType.NORMAL);
                }
            }
        }
    }

    public void tick() {
        if (!getActive().isEmpty()) prayerActiveTicks++;
        else prayerActiveTicks = 0;

        if (prayerActiveTicks > 0 && prayerActiveTicks % 2 == 0) {
            if (getPlayer().getSkills().getPrayerPoints() == 0) {
                playAudio(getPlayer(), Sounds.PRAYER_DRAIN_2672);
                getPlayer().sendMessage("You have run out of prayer points; you must recharge at an altar.");
                reset();
                return;
            }
            double amountDrain = 0;
            for (PrayerType type : getActive()) {
                double drain = type.getDrain();
                double bonus = (1 / 30f) * getPlayer().getProperties().getBonuses()[12];
                drain = drain * (1 + bonus);
                drain = 0.6 / drain;
                amountDrain += drain;
            }

            getPlayer().getSkills().decrementPrayerPoints(amountDrain);
        }
    }

    /**
     * Gets the skill bonus for the given skill id.
     *
     * @param skillId The skill id.
     * @return The bonus for the given skill.
     */
    public double getSkillBonus(int skillId) {
        double bonus = 0.0;
        for (PrayerType type : active) {
            for (SkillBonus b : type.getBonuses()) {
                if (b.getSkillId() == skillId) {
                    bonus += b.getBonus();
                }
            }
        }
        return bonus;
    }

    /**
     * Method used to check if we're permitted to toggle this prayer.
     *
     * @param type the type.
     * @return <code>True</code> if permitted to be toggled.
     */
    private boolean permitted(final PrayerType type) {
        return player.getSkills().getPrayerPoints() > 0 && type.permitted(player);
    }

    /**
     * Method used to return value of {@code True} if the {@link #active}
     * prayers contains the prayer type.
     *
     * @param type the type of prayer.
     * @return {@code True} if so.
     */
    public boolean get(PrayerType type) {
        return active.contains(type);
    }

    /**
     * Gets the player.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the active prayers.
     *
     * @return The active.
     */
    public List<PrayerType> getActive() {
        return active;
    }

}
