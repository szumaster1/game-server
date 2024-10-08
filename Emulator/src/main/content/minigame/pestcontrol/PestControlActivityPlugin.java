package content.minigame.pestcontrol;

import content.minigame.pestcontrol.npc.*;
import core.Configuration;
import core.game.activity.ActivityManager;
import core.game.activity.ActivityPlugin;
import core.game.component.Component;
import core.game.interaction.Option;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.impl.PulseManager;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.info.Rights;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.build.DynamicRegion;
import core.game.world.map.zone.RegionZone;
import core.game.world.map.zone.ZoneBuilder;
import core.game.world.map.zone.ZoneRestriction;
import core.plugin.PluginManager;
import core.plugin.Initializable;
import core.tools.RandomFunction;
import core.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static core.api.ContentAPIKt.curePoison;
import static core.api.ContentAPIKt.isPoisoned;

/**
 * Represents the Pest control activity plugin.
 */
@Initializable
public final class PestControlActivityPlugin extends ActivityPlugin {

    /**
     * The Max team size.
     */
    static final int MAX_TEAM_SIZE = 25;
    private static final int MIN_TEAM_SIZE = 5; //GameWorld.getSettings().isDevMode() ? 1 : 5;
    private final BoatType type;
    private final PriorityQueue<Player> waitingPlayers = new PriorityQueue<Player>(20, (player1, player2) -> {
        //get priorities of players. default to 0
        int p1 = player1.getAttribute("pc_prior", 0);
        int p2 = player2.getAttribute("pc_prior", 0);
        //return in descending order
        return p2 - p1;
    });
    private final List<PestControlSession> sessions = new ArrayList<>(20);
    private int ticks;
    private final Pulse pulse = new Pulse(1) {

        @Override
        public boolean pulse() {
            sessions.removeIf(session -> session != null && session.update());
            ticks++;
            if (waitingPlayers.size() >= MAX_TEAM_SIZE && ticks < 475) {
                ticks = 495;
            }
            if ((ticks < 450 && ticks % 100 == 0) || (ticks % 50 == 0) || ticks == 495) {
                for (Player p : waitingPlayers) {
                    updateTime(p);
                }
            }
            if (ticks >= 500) {
                if (waitingPlayers.size() >= MIN_TEAM_SIZE) {
                    PestControlActivityPlugin.this.start();
                } else {
                    ticks = 400;
                }
            }
            return false;
        }
    };


    /**
     * Instantiates a new Pest control activity plugin.
     */
    public PestControlActivityPlugin() {
        this(BoatType.NOVICE);
    }


    /**
     * Instantiates a new Pest control activity plugin.
     *
     * @param type the type
     */
    public PestControlActivityPlugin(BoatType type) {
        super("pest control " + type.name().toLowerCase(), false, true, true, ZoneRestriction.CANNON);
        this.safeRespawn = Location.create(2657, 2646, 0);
        this.type = type;
    }

    /**
     * Start.
     */
    public void start() {
        PestControlSession session = new PestControlSession(DynamicRegion.create(10536), this);
        session.startGame(waitingPlayers);
        session.getRegion().getRegionZones().add(new RegionZone(this, session.getRegion().getBorders()));
        sessions.add(session);
        ticks = 0;
        updatePlayerCount();
    }

    /**
     * End.
     *
     * @param session the session
     * @param success the success
     */
    public void end(PestControlSession session, boolean success) {
        if (!session.isActive()) {
            return;
        }
        for (final Player p : session.getRegion().getPlanes()[0].getPlayers()) {
            p.getProperties().setTeleportLocation(getLeaveLocation());
            if (!success) {
                p.getDialogueInterpreter().open(3781, true, 0, true);
                // default,
                // type,
                // default
            } else if (success && p.getAttribute("pc_zeal", 0) >= 50) {
                int amount = type.ordinal() + 2;
                p.getSavedData().activityData.increasePestPoints(amount);
                Item coins = new Item(995, p.getProperties().getCurrentCombatLevel() * 10);
                if (!p.getInventory().add(coins)) {
                    GroundItemManager.create(coins, p);
                }
                // default, type, name
                p.getDialogueInterpreter().open(3781, true, 1, type.ordinal() == 0 ? "two" : type.ordinal() == 1 ? "three" : "four");
            } else {
                // default type, default
                p.getDialogueInterpreter().open(3781, true, 2, true);
            }
            p.removeAttribute("pc_zeal");
            p.removeExtension(PestControlSession.class);
            p.fullRestore();
            if (isPoisoned(p)) {
                curePoison(p);
            }
            PulseManager.cancelDeathTask(p);
            GameWorld.getPulser().submit(new Pulse(1, p) {
                @Override
                public boolean pulse() {
                    p.getSkills().restore();
                    return true;
                }
            });
        }
        session.getRegion().getRegionZones().clear();
        session.setActive(false);
    }

    /**
     * Gets leave location.
     *
     * @return the leave location
     */
    public Location getLeaveLocation() {
        switch (type) {
            case NOVICE:
                return Location.create(2657, 2639, 0);
            case INTERMEDIATE:
                return Location.create(2644, 2644, 0);
            case VETERAN:
                return Location.create(2638, 2653, 0);
        }
        return Configuration.HOME_LOCATION;
    }

    @Override
    public boolean leave(Entity e, boolean logout) {
        if (e instanceof Player) {
            Player p = (Player) e;
            if (!logout) {
                p.getInterfaceManager().closeOverlay();
            } else {
                e.setLocation(getLeaveLocation());
                e.getProperties().setTeleportLocation(getLeaveLocation());
            }
            waitingPlayers.remove(p);
            updatePlayerCount();
        }
        return super.leave(e, logout);
    }

    @Override
    public void register() {
        if (type == BoatType.NOVICE) {
            PestControlActivityPlugin[] activities = new PestControlActivityPlugin[]{this, new PestControlActivityPlugin(BoatType.INTERMEDIATE), new PestControlActivityPlugin(BoatType.VETERAN)};
            ActivityManager.register(activities[1]);
            ActivityManager.register(activities[2]);
            // Load abstract NPC plugins
            PluginManager.definePlugin(new PCPortalNPC());
            PluginManager.definePlugin(new PCSquireNPC());
            PluginManager.definePlugin(new PCTorcherNPC());
            PluginManager.definePlugin(new PCDefilerNPC());
            PluginManager.definePlugin(new PCRavagerNPC());
            PluginManager.definePlugin(new PCShifterNPC());
            PluginManager.definePlugin(new PCSplatterNPC());
            PluginManager.definePlugin(new PCSpinnerNPC());
            PluginManager.definePlugin(new PCBrawlerNPC());
            PluginManager.definePlugin(new PCObjectHandler());
            PluginManager.definePlugin(new VoidSealPlugin());
            ZoneBuilder.configure(new PCLanderZone(activities));
            ZoneBuilder.configure(new PestControlIsland());
        }
        pulse.start();
        GameWorld.getPulser().submit(pulse);
    }

    @Override
    public boolean interact(Entity e, Node target, Option option) {
        return super.interact(e, target, option);
    }

    @Override
    public boolean start(Player p, boolean login, Object... args) {
        if (p.getProperties().getCurrentCombatLevel() < type.getRequirement() && p.getRights() != Rights.ADMINISTRATOR) {
            p.getPacketDispatch().sendMessage("You need a combat level of " + type.getRequirement() + " or higher to board this lander.");
            return false;
        }
        waitingPlayers.add(p);
        openLanderInterface(p);
        return true;
    }

    /**
     * Open lander interface.
     *
     * @param p the player instance to open the interface for.
     */
    public void openLanderInterface(Player p) {
        p.getInterfaceManager().openOverlay(new Component(407));
        updateTime(p);
        updatePlayerCount();
        p.getPacketDispatch().sendString("Points: " + p.getSavedData().activityData.getPestPoints(), 407, 16);
        p.getPacketDispatch().sendString(StringUtils.formatDisplayName(type.name()), 407, 3);
    }

    /**
     * Update time.
     *
     * @param p the player object that will receive the update.
     */
    public void updateTime(Player p) {
        // Calculate the remaining ticks until the next departure
        int ticks = 500 - this.ticks;

        // Check if there are more than 99 ticks remaining
        if (ticks > 99) {
            // Send a message to the player indicating the time until the next departure in minutes
            p.getPacketDispatch().sendString("Next Departure: " + (ticks / 100) + " min", 407, 13);
        }
        // Check if there are more than 50 ticks but less than or equal to 99
        else if (ticks > 50) {
            // Send a message to the player indicating the next departure is in 1 minute
            p.getPacketDispatch().sendString("Next Departure: 1 min", 407, 13);
        }
        // If there are 50 ticks or less remaining
        else {
            // Send a message to the player indicating the next departure is in 30 seconds
            p.getPacketDispatch().sendString("Next Departure: 30 seconds", 407, 13);
        }
    }

    /**
     * Update player count.
     */
    public void updatePlayerCount() {
        for (Player p : waitingPlayers) {
            p.getPacketDispatch().sendString("Players Ready: " + waitingPlayers.size(), 407, 14);
        }
    }

    @Override
    public boolean death(Entity e, Entity killer) {
        if (e instanceof Player && e.getViewport().getRegion().getRegionId() == 10536) {
            PestControlSession session = e.getExtension(PestControlSession.class);
            if (session != null) {
                Location l = session.getRegion().getBaseLocation();
                e.getProperties().setTeleportLocation(l.transform(32 + RandomFunction.RANDOM.nextInt(4), 49 + RandomFunction.RANDOM.nextInt(6), 0));
                return true;
            }
        }
        return super.death(e, killer);
    }

    // Override method to create a new instance of ActivityPlugin with a Player parameter
    @Override
    public ActivityPlugin newInstance(Player p) throws Throwable {
        return this; // Return the current instance of ActivityPlugin
    }

    // Override method to get the spawn location of the server
    @Override
    public Location getSpawnLocation() {
        return Configuration.HOME_LOCATION; // Return the predefined home location
    }

    // Override method to configure the plugin settings
    @Override
    public void configure() {
        registerRegion(10536); // Register a specific region with ID 10536
    }

    /**
     * Gets waiting players.
     *
     * @return the waiting players.
     */
    public PriorityQueue<Player> getWaitingPlayers() {
        return waitingPlayers; // Return the queue of players currently waiting
    }

    /**
     * Gets type.
     *
     * @return the type.
     */
    public BoatType getType() {
        return type; // Return the type of the boat
    }

    /**
     * The enum Boat type.
     */
    public enum BoatType {

        /**
         * Novice boat type.
         */
        NOVICE(40), // Define NOVICE boat type with a requirement of 40

        /**
         * Intermediate boat type.
         */
        INTERMEDIATE(70), // Define INTERMEDIATE boat type with a requirement of 70

        /**
         * Veteran boat type.
         */
        VETERAN(100);

        private final int requirement;

        BoatType(int requirement) {
            this.requirement = requirement;
        }

        /**
         * Gets requirement.
         *
         * @return the requirement.
         */
        public int getRequirement() {
            return requirement; // Return the requirement for the boat type
        }
    }
}
