package content.region.misthalin.lumbridge.quest.haunted.plugin;

import content.global.skill.support.agility.AgilityHandler;
import core.cache.def.impl.SceneryDefinition;
import core.game.global.action.DoorActionHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBuilder;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

import java.awt.*;
import java.util.Arrays;

import static core.api.ContentAPIKt.setVarp;

/**
 * Represents the Ernest the chicken plugin.
 */
@Initializable
public final class ErnestTheChickenPlugin extends OptionHandler {

    private static final Animation DOWN_ANIMATION = new Animation(2140);

    private static final Animation UP_ANIMATION = new Animation(2139);

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(11450).getHandlers().put("option:open", this);
        for (Lever lever : Lever.values()) {
            for (int i : lever.getObjectIds()) {
                SceneryDefinition.forId(i).getHandlers().put("option:pull", this);
                SceneryDefinition.forId(i).getHandlers().put("option:inspect", this);
            }
        }

        ZoneBuilder.configure(new LeverZone());
        return this;
    }

    @Override
    public boolean handle(final Player player, Node node, String option) {
        final Scenery object = ((Scenery) node);
        final LeverCacheExtension extension = LeverCacheExtension.extend(player);
        Lever lever = Lever.forObject(object.getId());
        switch (option) {
            case "pull":
                lever = Lever.forObject(object.getId());
                extension.pull(lever, object);
                break;
            case "inspect":
                extension.inspect(lever);
                break;
            case "open":
                extension.walk(object);
                return true;
        }
        return true;
    }

    /**
     * Lever cache extension.
     */
    public static class LeverCacheExtension {


        private static final int LEVER_CONFIG = 33;


        private static final int DOOR_CONFIG = 668;


        private final Player player;


        private final boolean[] levers = new boolean[Lever.values().length];


        /**
         * Instantiates a new Lever cache extension.
         *
         * @param player the player
         */
        public LeverCacheExtension(final Player player) {
            this.player = player;
        }


        /**
         * Extend lever cache extension.
         *
         * @param player the player
         * @return the lever cache extension
         */
        public static LeverCacheExtension extend(final Player player) {
            LeverCacheExtension extension = player.getExtension(LeverCacheExtension.class);
            if (extension == null) {
                extension = new LeverCacheExtension(player);
                player.addExtension(LeverCacheExtension.class, extension);
            }
            return extension;
        }


        /**
         * Pull.
         *
         * @param lever  the lever
         * @param object the object
         */
        public final void pull(final Lever lever, final Scenery object) {
            final boolean up = isUp(lever);
            levers[lever.ordinal()] = !up;
            player.animate(!up ? UP_ANIMATION : DOWN_ANIMATION);
            GameWorld.getPulser().submit(new Pulse(1) {
                @Override
                public boolean pulse() {
                    updateConfigs();
                    player.getPacketDispatch().sendMessage("You pull lever " + lever.name().replace("LEVER_", "").trim() + " " + (up ? "down" : "up") + ".");
                    player.getPacketDispatch().sendMessage("You hear a clunk.");
                    return true;
                }
            });
        }


        /**
         * Inspect.
         *
         * @param lever the lever
         */
        public final void inspect(final Lever lever) {
            player.getPacketDispatch().sendMessage("The lever is " + (isUp(lever) ? "up" : "down") + ".");
        }


        /**
         * Walk.
         *
         * @param object the object
         */
        public final void walk(final Scenery object) {
            player.lock(4);
            GameWorld.getPulser().submit(new Pulse(1, player, object) {
                @Override
                public boolean pulse() {
                    Point p = (Point) getWalkData()[0];
                    int[] rotation = (int[]) getWalkData()[1];
                    Location destination = (Location) getWalkData()[2];
                    final Scenery opened = object.transform(object.getId(), rotation[0]);
                    opened.setCharge(88);
                    opened.setLocation(object.getLocation().transform((int) p.getX(), (int) p.getY(), 0));
                    final Scenery second = DoorActionHandler.getSecondDoor(object, player);
                    SceneryBuilder.replace(object, opened, 2);
                    if (second != null) {
                        final Scenery secondOpened = second.transform(second.getId(), rotation[1]);
                        secondOpened.setCharge(88);
                        secondOpened.setLocation(second.getLocation().transform((int) p.getX(), (int) p.getY(), 0));
                        SceneryBuilder.replace(second, secondOpened, 2);
                    }
                    AgilityHandler.walk(player, 0, player.getLocation(), destination, null, 0, null);
                    return true;
                }
            });
        }


        /**
         * Update configs.
         */
        public final void updateConfigs() {
            setVarp(player, LEVER_CONFIG, calculateLeverConfig());
            setVarp(player, DOOR_CONFIG, calculateDoorConfig());
            save();
        }


        /**
         * Save.
         */
        public final void save() {
            boolean value = false;
            for (int index = 0; index < levers.length; index++) {
                value = levers[index];
                if (!value) {// down.
                    player.getSavedData().questData.getDraynorLevers()[index] = false;
                }
            }
        }


        /**
         * Read.
         */
        public final void read() {
            boolean value = false;
            for (int i = 0; i < Lever.values().length; i++) {
                value = player.getSavedData().questData.getDraynorLevers()[i];
                levers[i] = value;
            }
            updateConfigs();
        }


        /**
         * Calculate lever config int.
         *
         * @return the int
         */
        public final int calculateLeverConfig() {
            int value = 0;
            for (int i = 0; i < levers.length; i++) {
                if (!levers[i]) {
                    value += Math.pow(2, (i + 1));
                }
            }
            return value;
        }


        /**
         * Calculate door config int.
         *
         * @return the int
         */
        public final int calculateDoorConfig() {
            final int downCount = getDownCount();
            int value = 0;
            boolean up = false;
            Lever lever = null;
            for (int i = 0; i < levers.length; i++) {
                up = levers[i];// if its up.
                lever = Lever.values()[i];
                if (downCount == 0 || downCount == 1 && !isUp(Lever.LEVER_B) && lever == Lever.LEVER_B && levers[0]) {// translation:
                    // no
                    // down
                    // lever
                    // and
                    // lever
                    // b
                    // is
                    // on/off
                    // just
                    // send
                    // 0.
                    value = 0;
                    break;
                }
                if (downCount == 1 && !isUp(Lever.LEVER_A)) {
                    return 4;
                }
                if (downCount == 1 && !isUp(Lever.LEVER_D)) {
                    return 328;
                }
                if (downCount == 2 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_E)) {
                    return 290;
                }
                if (downCount == 2 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_F)) {
                    return 64;
                }
                if (downCount == 3 && !isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_A)) {
                    return 306;
                }
                if (downCount == 3 && !isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_F)) {
                    return 64;
                }
                if (downCount == 5 && !isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && !isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_B)) {
                    return 304;
                }
                if (!isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_E) && lever == Lever.LEVER_A) {
                    return 306;
                }
                if (!isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_E) && isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_B)) {
                    return 304;
                }
                if (!isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_B)) {
                    return 304;
                }
                if (downCount == 4 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && !isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_B)) {
                    return 264;
                }
                if (downCount == 3 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_E)) {
                    return 3;
                }
                if (downCount == 2 && !isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_F) && isUp(Lever.LEVER_D)) {
                    return 1;
                }
                if (downCount == 4 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_C)) {
                    return 19;
                }
                if (downCount == 3 && !isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C)) {
                    return 306;
                }
                if (downCount == 3 && isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && !isUp(Lever.LEVER_F)) {
                    return 306;
                }
                if (downCount == 2 && !isUp(Lever.LEVER_B) && !isUp(Lever.LEVER_D)) {
                    return 64;
                }
                if (downCount == 2 && !isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_D)) {
                    return 328;
                }
                if (lever == Lever.LEVER_A && up && !isUp(Lever.LEVER_B)) {
                    value -= 132;
                    continue;
                } else if (lever == Lever.LEVER_B && up && !isUp(Lever.LEVER_A)) {
                    value -= 64;
                    continue;
                }
                if (!up) {// if toggled.
                    if (lever == Lever.LEVER_A) {
                        value += 4;
                    } else if (lever == Lever.LEVER_B) {
                        value += 128;
                    } else if (lever == Lever.LEVER_D) {
                        value += 264;
                    } else if (lever == Lever.LEVER_C) {
                        value -= 132;
                    } else if (lever == Lever.LEVER_F) {
                        value -= 38;
                    } else if (lever == Lever.LEVER_E) {
                        value -= 287;
                    }
                }
            }
            return value;
        }


        /**
         * Get walk data object [ ].
         *
         * @return the object [ ]
         */
        public final Object[] getWalkData() {
            Object[] data = null;
            final int x = player.getLocation().getX();
            final int y = player.getLocation().getY();
            if (x == 3108 && y == 9757) {
                data = new Object[]{new Point(-1, 0), new int[]{2, 0}, Location.create(3108, 9759, 0)};
            } else if (x == 3108 && y == 9759) {
                data = new Object[]{new Point(-1, 0), new int[]{2, 0}, Location.create(3108, 9757, 0)};
            } else if (x == 3106 && y == 9760) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3104, 9760, 0)};
            } else if (x == 3104 && y == 9760) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3106, 9760, 0)};
            } else if (x == 3104 && y == 9765) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3106, 9765, 0)};
            } else if (x == 3106 && y == 9765) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3104, 9765, 0)};
            } else if (x == 3102 && y == 9764) {
                data = new Object[]{new Point(-1, 0), new int[]{2, 0}, Location.create(3102, 9762, 0),};
            } else if (x == 3102 && y == 9762) {
                data = new Object[]{new Point(-1, 0), new int[]{2, 0}, Location.create(3102, 9764, 0)};
            } else if (x == 3102 && y == 9759) {
                data = new Object[]{new Point(-1, 0), new int[]{2, 0}, Location.create(3102, 9757, 0)};
            } else if (x == 3102 && y == 9757) {
                data = new Object[]{new Point(-1, 0), new int[]{2, 0}, Location.create(3102, 9759, 0)};
            } else if (x == 3101 && y == 9760) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3099, 9760, 0)};
            } else if (x == 3099 && y == 9760) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3101, 9760, 0)};
            } else if (x == 3101 && y == 9765) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3099, 9765, 0)};
            } else if (x == 3099 && y == 9765) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3101, 9765, 0)};
            } else if (x == 3097 && y == 9762) {
                data = new Object[]{new Point(-1, 0), new int[]{2, 0}, Location.create(3097, 9764, 0)};
            } else if (x == 3097 && y == 9764) {
                data = new Object[]{new Point(-1, 0), new int[]{2, 0}, Location.create(3097, 9762, 0)};
            } else if (x == 3101 && y == 9755) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3099, 9755, 0)};
            } else if (x == 3099 && y == 9755) {
                data = new Object[]{new Point(0, 1), new int[]{3, 0}, Location.create(3101, 9755, 0)};
            }
            return data == null ? new Object[]{new Point(0, 0), new int[]{0, 0}, player.getLocation()} : data;
        }


        /**
         * Reset.
         */
        public void reset() {
            Arrays.fill(levers, true);
            Arrays.fill(player.getSavedData().questData.getDraynorLevers(), true);
            updateConfigs();
        }


        /**
         * Gets down count.
         *
         * @return the down count
         */
        public final int getDownCount() {
            int count = 0;
            for (int i = 0; i < levers.length; i++) {
                count += !levers[i] ? 1 : 0;
            }
            return count;
        }


        /**
         * Is up boolean.
         *
         * @param lever the lever
         * @return the boolean
         */
        public final boolean isUp(final Lever lever) {
            return levers[lever.ordinal()];
        }


        /**
         * Gets player.
         *
         * @return the player
         */
        public Player getPlayer() {
            return player;
        }

    }

    /**
     * Lever zone.
     */
    public class LeverZone extends MapZone {


        /**
         * Instantiates a new Lever zone.
         */
        public LeverZone() {
            super("Draynor lever zone", true);
        }

        @Override
        public void configure() {
            registerRegion(12440);
        }

        @Override
        public boolean enter(final Entity entity) {
            if (!(entity instanceof Player)) {
                return super.enter(entity);
            }
            final Player player = ((Player) entity);
            final LeverCacheExtension extension = LeverCacheExtension.extend(player);
            extension.read();
            return super.enter(entity);
        }

        @Override
        public boolean leave(final Entity entity, boolean logout) {
            if (entity instanceof Player) {
                final Player player = ((Player) entity);
                final LeverCacheExtension extension = LeverCacheExtension.extend(player);
                extension.save();
            }
            return super.leave(entity, logout);
        }
    }

    /**
     * The enum Lever.
     */
    public enum Lever {
        /**
         * Lever a lever.
         */
        LEVER_A(11451, 11452),
        /**
         * Lever b lever.
         */
        LEVER_B(11453, 11454),
        /**
         * Lever c lever.
         */
        LEVER_C(11455, 11456),
        /**
         * Lever d lever.
         */
        LEVER_D(11457, 11458),
        /**
         * Lever e lever.
         */
        LEVER_E(11459, 11460),
        /**
         * Lever f lever.
         */
        LEVER_F(11461, 11462);


        private final int[] objectIds;


        Lever(final int... objectId) {
            this.objectIds = objectId;
        }


        /**
         * Get object ids int [ ].
         *
         * @return the int [ ]
         */
        public int[] getObjectIds() {
            return objectIds;
        }


        /**
         * For object lever.
         *
         * @param objectId the object id
         * @return the lever
         */
        public static Lever forObject(final int objectId) {
            for (Lever lever : Lever.values()) {
                for (int i : lever.getObjectIds()) {
                    if (i == objectId) {
                        return lever;
                    }
                }
            }
            return null;
        }
    }

    @Override
    public Location getDestination(final Node node, final Node n) {
        if (n instanceof Scenery) {
            final Player player = ((Player) node);
            final int x = player.getLocation().getX();
            final int y = player.getLocation().getY();
            Location loc = n.getLocation();
            if (loc.equals(new Location(3108, 9758, 0))) {
                if (y <= 9757) {
                    return Location.create(3108, 9757, 0);
                } else {
                    return Location.create(3108, 9759, 0);
                }
            }
            if (loc.equals(new Location(3105, 9760, 0))) {
                if (x >= 3105) {
                    return Location.create(3106, 9760, 0);
                } else {
                    return Location.create(3104, 9760, 0);
                }
            }
            if (loc.equals(new Location(3105, 9765, 0))) {
                if (x >= 3106) {
                    return Location.create(3106, 9765, 0);
                } else {
                    return Location.create(3104, 9765, 0);
                }
            }
            if (loc.equals(new Location(3102, 9763, 0))) {
                if (y >= 9764) {
                    return Location.create(3102, 9764, 0);
                } else {
                    return Location.create(3102, 9762, 0);
                }
            }
            if (loc.equals(new Location(3102, 9758, 0))) {
                if (y >= 9759) {
                    return Location.create(3102, 9759, 0);
                } else {
                    return Location.create(3102, 9757, 0);
                }
            }
            if (loc.equals(new Location(3100, 9760, 0))) {
                if (x >= 3100) {
                    return Location.create(3101, 9760, 0);
                } else {
                    return Location.create(3099, 9760, 0);
                }
            }
            if (loc.equals(new Location(3100, 9765, 0))) {
                if (x >= 3100) {
                    return Location.create(3101, 9765, 0);
                } else {
                    return Location.create(3099, 9765, 0);
                }
            }
            if (loc.equals(new Location(3097, 9763, 0))) {
                if (y <= 9763) {
                    return Location.create(3097, 9762, 0);
                } else {
                    return Location.create(3097, 9764, 0);
                }
            }
            if (loc.equals(new Location(3100, 9755, 0))) {
                if (x >= 3100) {
                    return Location.create(3101, 9755, 0);
                } else {
                    return Location.create(3099, 9755, 0);
                }
            }
        }
        return null;
    }

}
