package core.game.node.entity.player.link;

import org.rs.consts.Sounds;
import core.Configuration;
import core.game.node.entity.Entity;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.player.Player;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;

import static core.api.ContentAPIKt.*;

/**
 * Handles the entity teleport.
 * @author SonicForce41, Woah
 */
public class TeleportManager {

    /**
     * The wildy teleport type.
     */
    public static final int WILDY_TELEPORT = 1 << 16 | 8;

    /**
     * The animations used in the home teleport.
     */
    private final static int[] HOME_ANIMATIONS = {1722, 1723, 1724, 1725, 2798, 2799, 2800, 3195, 4643, 4645, 4646, 4847, 4848, 4849, 4850, 4851, 4852, 65535};

    /**
     * The graphics used in the home teleport.
     */
    private final static int[] HOME_GRAPHICS = {775, 800, 801, 802, 803, 804, 1703, 1704, 1705, 1706, 1707, 1708, 1709, 1710, 1711, 1712, 1713, 65535};

    /**
     * The entity being handled.
     */
    private final Entity entity;

    /**
     * The last teleport of this <b>Entity</b>
     */
    private Pulse lastTeleport;

    /**
     * The current teleport of this <b>Entity</b>
     */
    private Pulse currentTeleport;

    /**
     * The current teleport type.
     */
    private int teleportType;

    /**
     * Constructs a new Teleport manager.
     *
     * @param entity the entity
     */
    public TeleportManager(Entity entity) {
        this.entity = entity;
        lastTeleport = TeleportType.HOME.getPulse(entity, Configuration.HOME_LOCATION);
    }

    /**
     * Send boolean.
     *
     * @param location the location
     * @return the boolean
     */
    public boolean send(Location location) {
        return send(location, entity instanceof Player ? getType((Player) entity) : TeleportType.NORMAL, 0);
    }

    /**
     * Send boolean.
     *
     * @param location the location
     * @param type     the type
     * @return the boolean
     */
    public boolean send(Location location, TeleportType type) {
        return send(location, type, 0);
    }

    /**
     * Send boolean.
     *
     * @param location     the location
     * @param type         the type
     * @param teleportType the teleport type
     * @return the boolean
     */
    public boolean send(Location location, TeleportType type, int teleportType) {
        if (teleportType == WILDY_TELEPORT || type == TeleportType.OBELISK) {
            if (hasTimerActive(entity, "teleblock")) return false;
        } else {
            if (!entity.getZoneMonitor().teleport(teleportType, null)) {
                return false;
            }
            if (teleportType != -1 && entity.isTeleBlocked()) {
                if (entity.isPlayer())
                    entity.asPlayer().sendMessage("A magical force has stopped you from teleporting.");
                return false;
            }
        }
        if (teleportType != -1) {
            if (entity instanceof Player) {
                Player p = (Player) entity;
                p.getDialogueInterpreter().close();
            }
        }
        if (entity.getAttribute("tablet-spell", false)) {
            type = TeleportType.TELETABS;
        }
        this.teleportType = teleportType;
        entity.getWalkingQueue().reset();
        lastTeleport = currentTeleport;
        currentTeleport = type.getPulse(entity, location);
        entity.getPulseManager().clear();
        if (type == TeleportType.HOME) {
            entity.getPulseManager().run(type.getPulse(entity, location));
        } else {
            entity.lock(12);
            entity.getImpactHandler().setDisabledTicks(teleportType == -1 ? 5 : 12);
            GameWorld.getPulser().submit(currentTeleport);
        }
        if (entity instanceof Player) {
            ((Player) entity).getInterfaceManager().close();
        }
        return true;
    }

    /**
     * Fire random.
     *
     * @param entity   the entity
     * @param location the location
     */
    public static void fireRandom(Entity entity, Location location) {
        if (entity instanceof Player && entity.getTeleporter().getTeleportType() == 0) {
            Player p = (Player) entity;
        }
    }

    private static int getAudio(int count) {
        switch (count) {
            case 0:
                return 193;
            case 4:
                return 194;
            case 11:
                return 195;
        }
        return -1;
    }

    /**
     * Gets entity.
     *
     * @return the entity
     */
    public final Entity getEntity() {
        return entity;
    }

    /**
     * Gets last teleport.
     *
     * @return the last teleport
     */
    public final Pulse getLastTeleport() {
        return lastTeleport;
    }

    /**
     * Gets current teleport.
     *
     * @return the current teleport
     */
    public final Pulse getCurrentTeleport() {
        return currentTeleport;
    }


    /**
     * The enum Teleport type.
     */
    public enum TeleportType {

        /**
         * The Normal.
         */
        NORMAL(new TeleportSettings(8939, 8941, 1576, 1577)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELEPORT_ALL_200);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(getSettings().getStartGfx()));
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            fireRandom(entity, location);
                        } else if (delay == 4) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELEPORT_REVERSE_201);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
                            entity.graphics(new Graphic(getSettings().getEndGfx()));
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.unlock();
                        entity.lock(4);
                    }
                };
            }
        },
        /**
         * The Ancient.
         */
        ANCIENT(new TeleportSettings(1979, -1, 392, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            playGlobalAudio(entity.getLocation(), Sounds.BLOCK_TELEPORT_197, 0, 7);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(getSettings().getStartGfx()));
                        } else if (delay == 4) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            fireRandom(entity, location);
                        } else if (delay == 5) {
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
                            entity.graphics(new Graphic(getSettings().getEndGfx()));
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.getAnimator().forceAnimation(new Animation(-1));
                        entity.graphics(new Graphic(-1));
                        entity.unlock();
                    }
                };
            }
        },
        /**
         * The Lunar.
         */
        LUNAR(new TeleportSettings(1816, -1, 747, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            entity.graphics(new Graphic(getSettings().getStartGfx(), 120));
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            fireRandom(entity, location);
                        } else if (delay == 4) {
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
                            entity.graphics(new Graphic(getSettings().getEndGfx()));
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.getAnimator().forceAnimation(new Animation(-1));
                        entity.graphics(new Graphic(-1));
                        entity.unlock();
                    }
                };
            }
        },
        /**
         * The Teletabs.
         */
        TELETABS(new TeleportSettings(4731, -1, 678, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            playGlobalAudio(entity.getLocation(), Sounds.POH_TABLET_BREAK_979);
                            entity.getAnimator().forceAnimation(new Animation(4069));
                        } else if (delay == 2) {
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote(), Priority.HIGH));
                            entity.graphics(new Graphic(getSettings().getStartGfx()));
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                        } else if (delay == 4) {
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote()));
                            entity.graphics(new Graphic(getSettings().getEndGfx()));
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.getAnimator().forceAnimation(new Animation(-1));
                        entity.graphics(new Graphic(-1));
                        entity.unlock();
                        entity.lock(2);
                    }
                };
            }
        },
        /**
         * The Home.
         */
        HOME(new TeleportSettings(4847, 4857, 800, 804)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int count;
                    Player player;

                    @Override
                    public boolean pulse() {
                        switch (count) {
                            case 18:
                                player.getProperties().setTeleportLocation(location);
                                return true;
                            default:
                                playGlobalAudio(entity.getLocation(), getAudio(count));
                                player.getPacketDispatch().sendGraphic(HOME_GRAPHICS[count]);
                                player.getPacketDispatch().sendAnimation(HOME_ANIMATIONS[count]);
                                break;
                        }
                        count++;
                        return false;
                    }

                    @Override
                    public void start() {
                        player = ((Player) entity);
						/*if (player.getSavedData().getGlobalData().getHomeTeleportDelay() > System.currentTimeMillis() && !player.isDonator()) {
						    long milliseconds = player.getSavedData().getGlobalData().getHomeTeleportDelay() - System.currentTimeMillis();
						    int minutes = (int) Math.round(milliseconds / 120000);
						    if (minutes > 15) {
						    	player.getSavedData().getGlobalData().setHomeTeleportDelay(System.currentTimeMillis() + 1200000);
						    	milliseconds = player.getSavedData().getGlobalData().getHomeTeleportDelay() - System.currentTimeMillis();
						    	minutes = (int) Math.round(milliseconds / 120000);
						    }
						    if (minutes != 0) {
						    	player.getPacketDispatch().sendMessage("You need to wait another " + minutes + " " + (minutes == 1 ? "minute" : "minutes") + " to cast this spell.");
						    	stop();
						    	return;
						    }
						}*/
                        super.start();
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.getAnimator().forceAnimation(new Animation(-1));
                        player.graphics(new Graphic(-1));
                    }
                };
            }
        },
        /**
         * The Obelisk.
         */
        OBELISK(new TeleportSettings(8939, 8941, 661, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            entity.lock();
                            entity.getAnimator().forceAnimation(new Animation(1816));
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                        } else if (delay == 4) {
                            entity.getAnimator().forceAnimation(Animation.RESET);
                            entity.unlock();
                            return true;
                        }
                        delay++;
                        return false;
                    }
                };
            }
        },
        /**
         * The Tele other.
         */
        TELE_OTHER(new TeleportSettings(1816, -1, 342, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELE_OTHER_CAST_199);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(getSettings().getStartGfx()));
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                        } else if (delay == 4) {
                            entity.getAnimator().forceAnimation(new Animation(-1));
                            entity.unlock();
                            return true;
                        }
                        delay++;
                        return false;
                    }
                };
            }
        },
        /**
         * The Fairy ring.
         */
        FAIRY_RING(new TeleportSettings(-1, -1, -1, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                entity.graphics(Graphic.create(569));
                return new TeleportPulse(entity) {
                    int delay;

                    @Override
                    public boolean pulse() {
                        switch (++delay) {
                            case 2:
                                entity.animate(Animation.create(3265));
                                if (entity instanceof Player) {
                                    playAudio(entity.asPlayer(), Sounds.FT_FAIRY_TELEPORT_1098);
                                }
                                break;
                            case 4:
                                entity.animate(Animation.create(3266));
                                entity.getProperties().setTeleportLocation(location);
                                entity.unlock();
                                entity.lock(2);
                                return true;
                        }
                        return false;
                    }

                };
            }
        },
        /**
         * The Puro puro.
         */
        PURO_PURO(new TeleportSettings(6601, 1118, -1, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(1118));
                        } else if (delay == 9) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            entity.getAnimator().forceAnimation(new Animation(-1));
                            entity.unlock();
                            return true;
                        }
                        delay++;
                        return false;
                    }
                };
            }
        },
        /**
         * The Ectophial.
         */
        ECTOPHIAL(new TeleportSettings(8939, 8941, 1587, 1588)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELEPORT_ALL_200);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(getSettings().getStartGfx()));
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            fireRandom(entity, location);
                        } else if (delay == 4) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELEPORT_REVERSE_201);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
                            entity.graphics(new Graphic(getSettings().getEndGfx()));
                            return true;
                        }
                        delay++;
                        return false;
                    }
                };
            }
        },
        /**
         * The Christmas.
         */
        CHRISTMAS(new TeleportSettings(7534, -1, 1292, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELEPORT_ALL_200);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(getSettings().getStartGfx()));
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            fireRandom(entity, location);
                        } else if (delay == 4) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELEPORT_REVERSE_201);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
                            entity.graphics(new Graphic(getSettings().getEndGfx()));
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.unlock();
                        entity.lock(4);
                    }
                };
            }
        },
        /**
         * The Cabbage.
         */
        CABBAGE(new TeleportSettings(9984, 9986, 1731, 1732)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            if (entity instanceof Player) {
                                playAudio(entity.asPlayer(), 5036);
                            }
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(getSettings().getStartGfx()));
                        } else if (delay == 5) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            fireRandom(entity, location);
                            if (entity instanceof Player) {
                                playAudio(entity.asPlayer(), 5034);
                            }
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
                            entity.graphics(new Graphic(getSettings().getEndGfx()));
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.unlock();
                        entity.lock(4);
                    }
                };
            }
        },
        /**
         * The Entrana magic door.
         */
        ENTRANA_MAGIC_DOOR(new TeleportSettings(10100, 9013, 1745, 1747)) { //

            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELEPORT_ALL_200);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(getSettings().getStartGfx()));
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            fireRandom(entity, location);
                        } else if (delay == 4) {
                            playGlobalAudio(entity.getLocation(), Sounds.TELEPORT_REVERSE_201);
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
                            entity.graphics(new Graphic(getSettings().getEndGfx()));
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.unlock();
                        entity.lock(4);
                    }
                };
            }
        },
        /**
         * The Random event old.
         */
        RANDOM_EVENT_OLD(new TeleportSettings(714, -1, -1, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;
                    Player player;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(308, 100, 50));
                        } else if (delay == 4) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            entity.getAnimator().forceAnimation(new Animation(-1));
                            entity.unlock();
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void start() {

                        super.start();
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.getAnimator().forceAnimation(new Animation(-1));
                        entity.graphics(new Graphic(-1));
                    }
                };
            }
        },
        /**
         * The Minigame.
         */
        MINIGAME(new TeleportSettings(6601, 1118, -1, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;
                    Player player;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
                            entity.graphics(new Graphic(1118));
                        } else if (delay == 9) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                            entity.getAnimator().forceAnimation(new Animation(-1));
                            entity.unlock();
                            return true;
                        }
                        delay++;
                        return false;
                    }

                    @Override
                    public void start() {

                        super.start();
                    }

                    @Override
                    public void stop() {
                        super.stop();
                        entity.getAnimator().forceAnimation(new Animation(-1));
                        entity.graphics(new Graphic(-1));
                    }
                };
            }
        },
        /**
         * The Instant.
         */
        INSTANT(new TeleportSettings(-1, -1, -1, -1)) {
            @Override
            public Pulse getPulse(final Entity entity, final Location location) {
                return new TeleportPulse(entity) {
                    int delay = 0;

                    @Override
                    public boolean pulse() {
                        if (delay == 0) {
                            entity.lock();
                        } else if (delay == 3) {
                            entity.getProperties().setTeleportLocation(Location.create(location));
                        } else if (delay == 4) {
                            entity.getAnimator().forceAnimation(Animation.RESET);
                            entity.unlock();
                            return true;
                        }
                        delay++;
                        return false;
                    }
                };
            }
        };

        private TeleportSettings settings;

        /**
         * Gets pulse.
         *
         * @param entity   the entity
         * @param location the location
         * @return the pulse
         */
        public abstract Pulse getPulse(final Entity entity, final Location location);

        TeleportType(TeleportSettings settings) {
            this.settings = settings;
        }

        /**
         * Gets settings.
         *
         * @return the settings
         */
        public final TeleportSettings getSettings() {
            return settings;
        }
    }

    /**
     * Teleport settings.
     */
    static class TeleportSettings {
        private int startAnim;
        private int endAnim;
        private int startGFX;
        private int endGFX;

        /**
         * Constructs a new Teleport settings.
         *
         * @param startAnim the start anim
         * @param endAnim   the end anim
         * @param startGfx  the start gfx
         * @param endGfx    the end gfx
         */
        public TeleportSettings(int startAnim, int endAnim, int startGfx, int endGfx) {
            this.startAnim = startAnim;
            this.endAnim = endAnim;
            this.startGFX = startGfx;
            this.endGFX = endGfx;
        }

        /**
         * Gets start emote.
         *
         * @return the start emote
         */
        public final int getStartEmote() {
            return startAnim;
        }

        /**
         * Gets end emote.
         *
         * @return the end emote
         */
        public final int getEndEmote() {
            return endAnim;
        }

        /**
         * Gets start gfx.
         *
         * @return the start gfx
         */
        public final int getStartGfx() {
            return startGFX;
        }

        /**
         * Gets end gfx.
         *
         * @return the end gfx
         */
        public final int getEndGfx() {
            return endGFX;
        }
    }

    /**
     * Gets type.
     *
     * @param player the player
     * @return the type
     */
    public static TeleportType getType(Player player) {
        switch (player.getSpellBookManager().getSpellBook()) {
            case 193:
                return TeleportType.ANCIENT;
            case 430:
                return TeleportType.LUNAR;
        }
        return TeleportType.NORMAL;
    }

    /**
     * Gets teleport type.
     *
     * @return the teleport type
     */
    public int getTeleportType() {
        return teleportType;
    }

    /**
     * Sets teleport type.
     *
     * @param teleportType the teleport type
     */
    public void setTeleportType(int teleportType) {
        this.teleportType = teleportType;
    }
}

