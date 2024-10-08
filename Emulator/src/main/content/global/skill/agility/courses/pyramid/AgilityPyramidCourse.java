package content.global.skill.agility.courses.pyramid;

import content.global.skill.agility.AgilityCourse;
import content.global.skill.agility.AgilityHandler;
import core.cache.def.impl.SceneryDefinition;
import core.cache.def.impl.VarbitDefinition;
import core.game.global.action.ClimbActionHandler;
import core.game.node.Node;
import core.game.node.entity.impl.ForceMovement;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.PluginManager;
import org.rs.consts.Sounds;

import static core.api.ContentAPIKt.*;

/**
 * Agility pyramid course.
 */
@Initializable
public final class AgilityPyramidCourse extends AgilityCourse {

    private static final Item PYRAMID_TOP = new Item(6970);

    private static final int CONFIG_ID = 640;

    private static final Location[][] GAP_LOCATIONS = new Location[][]{{new Location(3356, 2847, 2), Location.create(3357, 2847, 2), Location.create(3356, 2846, 2), Location.create(3357, 2846, 2), Location.create(3356, 2849, 2), Location.create(3357, 2849, 2)}, {new Location(3364, 2833, 2), Location.create(3364, 2834, 2), Location.create(3366, 2834, 2), Location.create(3366, 2833, 2), Location.create(3363, 2834, 2), Location.create(3363, 2833, 2)}, {new Location(3370, 2841, 3), new Location(3371, 2841, 3), Location.create(3370, 2840, 3), Location.create(3371, 2840, 3), Location.create(3370, 2843, 3), Location.create(3371, 2843, 3)}, {new Location(3040, 4697, 2), Location.create(3041, 4697, 2), Location.create(3041, 4696, 2), Location.create(3040, 4696, 2), Location.create(3041, 4699, 2), Location.create(3040, 4699, 2)}, {new Location(3048, 4695, 2), Location.create(3049, 4695, 2), Location.create(3048, 4694, 2), Location.create(3049, 4694, 2), Location.create(3048, 4697, 2), Location.create(3049, 4697, 2)}, {Location.create(3046, 4697, 3), Location.create(3047, 4697, 3), Location.create(3046, 4696, 3), Location.create(3047, 4696, 3), Location.create(3046, 4699, 3), Location.create(3047, 4699, 3)}};

    private static final Location[][] LEDGE_LOCATIONS = new Location[][]{{new Location(3366, 2851, 1), new Location(3364, 2851, 1), Location.create(3368, 2851, 1), Location.create(3363, 2851, 1), Location.create(3367, 2851, 1), Location.create(3364, 2851, 1)}, {new Location(3362, 2831, 1), new Location(3360, 2831, 1), Location.create(3364, 2832, 1), Location.create(3359, 2832, 1), Location.create(3360, 2832, 1), Location.create(3363, 2832, 1)}, {new Location(3372, 2837, 2), new Location(3372, 2839, 2), Location.create(3372, 2841, 2), Location.create(3372, 2836, 2), Location.create(3372, 2837, 2), Location.create(3372, 2840, 2)}, {new Location(3358, 2843, 3), new Location(3358, 2845, 3), Location.create(3359, 2842, 3), Location.create(3359, 2847, 3), Location.create(3359, 2846, 3), Location.create(3359, 2843, 3)}};

    private static final Location[][] GAP_CROSS_LOCATIONS = new Location[][]{{new Location(3368, 2831, 1), new Location(3370, 2831, 1), Location.create(3372, 2832, 1), Location.create(3367, 2832, 1), Location.create(3371, 2832, 1), Location.create(3368, 2832, 1)}, {new Location(3356, 2837, 2), new Location(3356, 2839, 2), Location.create(3357, 2841, 2), Location.create(3357, 2836, 2), Location.create(3357, 2840, 2), Location.create(3357, 2837, 2)}, {new Location(3360, 2849, 2), new Location(3362, 2849, 2), Location.create(3359, 2849, 2), Location.create(3364, 2849, 2), Location.create(3363, 2849, 2), Location.create(3360, 2849, 2)}};

    /**
     * Instantiates a new Agility pyramid course.
     */
    public AgilityPyramidCourse() {
        this(null);
    }

    /**
     * Instantiates a new Agility pyramid course.
     *
     * @param player the player
     */
    public AgilityPyramidCourse(Player player) {
        super(player, 5, 0);
    }

    @Override
    public void configure() {
        SceneryDefinition.forId(16535).getHandlers().put("option:climb", this);
        SceneryDefinition.forId(16536).getHandlers().put("option:climb", this);
        SceneryDefinition.forId(10851).getHandlers().put("option:climb", this);
        SceneryDefinition.forId(10855).getHandlers().put("option:enter", this);
        SceneryDefinition.forId(10856).getHandlers().put("option:enter", this);
        SceneryDefinition.forId(10860).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10861).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10862).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10863).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10864).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10868).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10867).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10882).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10883).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10884).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10885).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10886).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10887).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10888).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10889).getHandlers().put("option:cross", this);
        SceneryDefinition.forId(10859).getHandlers().put("option:jump", this);
        SceneryDefinition.forId(10857).getHandlers().put("option:climb-up", this);
        SceneryDefinition.forId(10858).getHandlers().put("option:climb-down", this);
        SceneryDefinition.forId(10865).getHandlers().put("option:climb-over", this);
        RollingBlock.BlockSets.values();
        PluginManager.definePlugin(new MovingBlockNPC());
        PluginManager.definePlugin(new AgilityPyramidZone());
    }

    @Override
    public boolean handle(final Player player, Node node, String option) {
        getCourse(player); // Sets the extension.
        final Scenery object = (Scenery) node;
        if (object.getLocation().getDistance(player.getLocation()) >= 3) {
            player.getPacketDispatch().sendMessage("I can't reach that!");
            return true;
        }
        switch (object.getId()) {
            case 16535:
            case 16536:
                handleRockClimb(player, object);
                break;
            case 10865:
                handleLowWall(player, object);
                break;
            case 10888:
            case 10889:
            case 10860:
            case 10886:
            case 10887:
                handleLedge(player, object);
                break;
            case 10868:
            case 10867:
                handlePlank(player, object);
                break;
            case 10882:
            case 10883:
            case 10884:
            case 10885:
            case 10861:
            case 10862:
            case 10863:
            case 10864:
                handleGapCross(player, object);
                break;
            case 10857:
            case 10858:
                handleStairs(player, object);
                break;
            case 10851:
                handlePyramidTop(player, object);
                break;
            case 10859:
                handleJumpGap(player, object);
                break;
            case 10855:
            case 10856:
                addConfig(player, 10869, 0, true);
                if (getVarp(player, 640) == 505 || getVarp(player, 640) == 515) {
                    player.getSkills().addExperience(Skills.AGILITY, 300, true);
                }
                player.getDialogueInterpreter().sendDialogue("You climb down the steep passage. It leads to the base of the", "pyramid.");
                player.getProperties().setTeleportLocation(Location.create(3364, 2830, 0));
                player.getSavedData().activityData.setTopGrabbed(false);
                break;
        }
        return true;
    }

    private void handleStairs(final Player player, final Scenery object) {
        Direction dir = Direction.getLogicalDirection(player.getLocation(), object.getLocation());
        Location dest = null;
        if (dir == Direction.NORTH) {
            dest = object.getLocation().transform(0, 2, 1);
        } else if (dir == Direction.SOUTH) {
            dest = object.getLocation().transform(0, -1, -1);
        }
        if (object.getLocation().equals(Location.create(3360, 2837, 3))) {
            dest = Location.create(3040, 4695, 2);
        } else if (object.getLocation().equals(Location.create(3040, 4693, 2))) {
            dest = Location.create(3360, 2836, 3);
        } else if (object.getLocation().equals(new Location(3354, 2831, 0))) {
            addConfig(player, 10869, 0, true);
        }
        ClimbActionHandler.climb(player, null, dest);
    }

    private void handleRockClimb(final Player player, final Scenery object) {
        final boolean scale = player.getLocation().getX() < object.getLocation().getX();
        final Location end = object.getLocation().transform(scale ? 2 : -2, 0, 0);
        if (object.getId() == 16536 && player.getSkills().getStaticLevel(Skills.AGILITY) < 30) {
            player.getPacketDispatch().sendMessage("You must be level 30 agility or higher to climb down the rocks.");
            return;
        }
        playAudio(player, Sounds.CLIMBING_LOOP_2454, 0, 6);
        if (!scale) {
            ForceMovement.run(player, player.getLocation(), end, Animation.create(740), Animation.create(740), Direction.WEST, 13).setEndAnimation(Animation.RESET);
        } else {
            ForceMovement.run(player, player.getLocation(), end, Animation.create(1148), Animation.create(1148), Direction.WEST, 13).setEndAnimation(Animation.RESET);
        }
    }

    private void handleLowWall(final Player player, final Scenery object) {
        Direction d = Direction.getDirection(player.getLocation(), object.getLocation());
        final boolean fail = player.getSkills().getLevel(Skills.AGILITY) < 75 && hasFailed(player);
        if (player.getLocation().equals(Location.create(3355, 2848, 1)) || player.getLocation().equals(Location.create(3359, 2838, 3))) {
            d = Direction.NORTH;
        } else if (player.getLocation().equals(Location.create(3355, 2850, 1)) || player.getLocation().equals(Location.create(3359, 2840, 3))) {
            d = Direction.SOUTH;
        } else if (player.getLocation().equals(Location.create(3046, 4694, 2)) || player.getLocation().equals(Location.create(3355, 2850, 1)) || player.getLocation().equals(Location.create(3041, 4702, 2)) || player.getLocation().equals(new Location(3369, 2834, 2))) {
            d = Direction.EAST;
        } else if (player.getLocation().equals(Location.create(3048, 4694, 2)) || player.getLocation().equals(Location.create(3371, 2834, 2)) || player.getLocation().equals(Location.create(3043, 4702, 2))) {
            d = Direction.WEST;
        }
        player.lock(4);
        player.getPacketDispatch().sendMessage("You climb the low wall...");
        if (fail) {
            Location end = player.getLocation().transform(d, 1);
            player.lock(3);
            playAudio(player, Sounds.CLIMB_WALL_2453, 40);
            AgilityHandler.failWalk(player, 2, player.getLocation(), end, end, Animation.create(1106), 15, getHitAmount(player), "You lost your balance!");
            AgilityHandler.forceWalk(player, -1, end, player.getLocation(), ForceMovement.WALK_ANIMATION, 10, 0.0, null, 4);
            return;
        }
        playAudio(player, Sounds.CLIMB_WALL_2453, 10, 40);
        AgilityHandler.forceWalk(player, 0, player.getLocation(), player.getLocation().transform(d, 2), Animation.create(1252), 6, 8, "... and make it over.");
        player.animate(Animation.RESET, 4);
    }

    private void handleLedge(final Player player, final Scenery object) {
        Direction d = Direction.getLogicalDirection(player.getLocation(), getLedgeLocation(player, object));
        final Direction dir = d;
        final int diff = object.getRotation() == 3 && dir == Direction.EAST ? 1 : object.getRotation() == 3 && dir == Direction.WEST ? 0 : d == Direction.EAST || (d == Direction.SOUTH && object.getRotation() != 0) || d == Direction.NORTH ? 0 : 1;
        final boolean fail = player.getSkills().getLevel(Skills.AGILITY) < 75 && hasFailed(player);
        final Location end = player.getLocation().transform(dir.getStepX() * (fail ? 3 : 5), dir.getStepY() * (fail ? 3 : 5), 0);
        player.getPacketDispatch().sendMessage("You put your foot on the ledge and try to edge across...");
        playAudio(player, Sounds.BALANCING_LEDGE_2451, 0, 5);
        if (fail) {
            player.lock(4);
            playAudio(player, Sounds.FALL_LAND_2455, 125);
            AgilityHandler.walk(player, -1, player.getLocation(), end, Animation.create(157 - diff), 0.0, "You slip and fall to the level below.");
            GameWorld.getPulser().submit(new Pulse(3, player) {
                @Override
                public boolean pulse() {
                    Location dest = end;
                    int x = 0, y = 0;
                    switch (dir) {
                        case NORTH:
                        case SOUTH:
                            if (object.getRotation() == 2) {
                                x = 2;
                            } else {
                                x = -2;
                            }
                            break;
                        case WEST:
                        case EAST:
                            if (object.getRotation() == 3) {
                                y = -2;
                            } else if (object.getRotation() == 1) {
                                y = 2;
                            }
                            break;
                        default:
                            break;
                    }
                    AgilityHandler.fail(player, 1, transformLevel(dest.transform(x, y, 0)), Animation.create(761 - diff), 10, null);
                    return true;
                }
            });
            return;
        }
        AgilityHandler.walk(player, 3, player.getLocation(), end, Animation.create(157 - diff), 52, "You skillfully edge across the gap.");
    }

    private void handlePlank(final Player player, final Scenery object) {
        final boolean custom = object.getLocation().equals(new Location(3365, 2835, 3)) || object.getLocation().equals(new Location(3370, 2835, 3));
        final Direction dir = custom ? Direction.EAST : Direction.getLogicalDirection(player.getLocation(), object.getLocation());
        final boolean fail = player.getSkills().getLevel(Skills.AGILITY) < 75 && hasFailed(player);
        final Location end = object.getLocation().transform(object.getId() != 10868 ? dir : dir.getOpposite(), fail ? 2 : 5);
        AgilityHandler.walk(player, fail ? -1 : 1, player.getLocation(), end, Animation.create(155), fail ? 0.0 : 56.4, fail ? null : "You walk carefully across the slippery plank...");
        playAudio(player, Sounds.PLANKWALK_2480, 0, 3);
        if (fail) {
            GameWorld.getPulser().submit(new Pulse(2, player) {
                @Override
                public boolean pulse() {
                    final Location dest = transformLevel(end.transform(!custom ? 2 : 0, custom ? -2 : 0, 0));
                    playAudio(player, Sounds.FALL_LAND_2455, 10, 50);
                    AgilityHandler.failWalk(player, 2, end, dest, dest, Animation.create(764), 10, 10, null);
                    return true;
                }
            });
        }
    }

    private void handleJumpGap(Player player, Scenery object) {
        final Direction dir = Direction.getDirection(player.getLocation(), getGapLocation(player, object));
        final boolean fail = player.getSkills().getLevel(Skills.AGILITY) < 75 && hasFailed(player);
        player.getPacketDispatch().sendMessage("You jump the gap...");
        if (fail) {
            final boolean regular = object.getRotation() == 0 || object.getRotation() == 3;
            Location end = player.getLocation().transform(dir, 1);
            Location dest = object.getLocation().transform(dir, 1);
            if (!regular) {
                dest = dest.transform(dir == Direction.NORTH || dir == Direction.SOUTH ? 2 : 0, dir == Direction.SOUTH ? 1 : 0, 0);
            } else {
                dest = dest.transform(dir == Direction.NORTH || dir == Direction.SOUTH ? -1 : dir == Direction.WEST ? 1 : 0, dir == Direction.SOUTH ? 1 : dir == Direction.WEST || dir == Direction.EAST ? -1 : 0, 0);
            }
            dest = transformLevel(dest);
            player.lock(8);
            playAudio(player, Sounds.JUMP_NO_LAND_2467, 30);
            playAudio(player, Sounds.FALL_LAND_2455, 200);
            AgilityHandler.forceWalk(player, -1, player.getLocation(), end, Animation.create(3068), 10, 0.0, "... and miss your footing.");
            AgilityHandler.fail(player, 8, dest, Animation.create(3068), 8, null);
            return;
        }
        playAudio(player, Sounds.JUMP2_2462, 30);
        player.lock(4);
        AgilityHandler.forceWalk(player, 2, player.getLocation(), player.getLocation().transform(dir, 3), Animation.create(3067), 20, 22, null);
    }

    private void handleGapCross(final Player player, final Scenery object) {
        final Direction dir = Direction.getLogicalDirection(player.getLocation(), getGapCrossLocation(player, object));
        final boolean fail = player.getSkills().getLevel(Skills.AGILITY) < 75 && hasFailed(player);
        final Location end = player.getLocation().transform(dir, fail ? 4 : 5);
        final int rot = object.getRotation();
        int mod = player.getLocation().equals(new Location(3359, 2849, 2)) ? 0 : player.getLocation().equals(new Location(3357, 2841, 2)) ? 1 : player.getLocation().equals(new Location(3367, 2832, 1)) ? 1 : player.getLocation().equals(new Location(3372, 2832, 1)) ? 0 : object.getLocation().equals(new Location(3370, 2831, 1)) ? 0 : rot == 1 && dir == Direction.EAST ? 0 : rot == 3 && (dir == Direction.WEST || dir == Direction.EAST) ? 1 : rot == 0 && dir == Direction.SOUTH ? 1 : dir == Direction.WEST && rot != 3 || dir == Direction.EAST ? 1 : 0;
        final Animation animation = Animation.create(387 - mod);
        playAudio(player, Sounds.HANDHOLDS_GRAB_TO_SECOND_2450);
        playAudio(player, Sounds.FALL_LAND_2455, 170);
        if (fail) {
            Location dest = object.getLocation().transform(dir, 1);
            dest = rot == 1 && dir == Direction.EAST ? dest.transform(1, 2, 0) : rot == 1 && dir == Direction.WEST && player.getLocation().getY() < 2841 ? dest.transform(0, -2, 0) : rot == 1 && dir == Direction.WEST ? dest.transform(0, 2, 0) : dest.transform(dir == Direction.NORTH || dir == Direction.SOUTH ? -1 : dir == Direction.WEST ? 1 : 0, dir == Direction.SOUTH ? 1 : dir == Direction.WEST || dir == Direction.EAST ? -1 : 0, 0);
            AgilityHandler.walk(player, -1, player.getLocation(), end, animation, 10, null);
            Location finalDest = dest;
            GameWorld.getPulser().submit(new Pulse(3, player) {
                @Override
                public boolean pulse() {
                    AgilityHandler.fail(player, 3, transformLevel(finalDest), Animation.create(3056 - mod), 8, null);
                    player.getAppearance().setDefaultAnimations();
                    player.getAppearance().sync();
                    return true;
                }
            });
            return;
        }
        AgilityHandler.walk(player, 4, player.getLocation(), end, animation, 22, null);
    }

    private void handlePyramidTop(final Player player, final Scenery object) {
        if (player.getSavedData().activityData.isTopGrabbed()) {
            player.getPacketDispatch().sendMessage("You've already claimed this!");
        } else {
            player.animate(Animation.create(3063));
            GameWorld.getPulser().submit(new Pulse(3, player) {
                @Override
                public boolean pulse() {
                    player.getSkills().addExperience(Skills.AGILITY, 1000);
                    if (getVarp(player, 640) == 505 || getVarp(player, 640) == 515) {
                        player.getPacketDispatch().sendMessage("You find nothing on top of the pyramid.");
                        return true;
                    }
                    addConfig(player, 10869, 1, true);
                    player.getSavedData().activityData.setTopGrabbed(true);
                    player.getInventory().add(PYRAMID_TOP, player);
                    player.getDialogueInterpreter().sendItemMessage(PYRAMID_TOP, "You find a golden pyramid!");
                    playJingle(player, 151);
                    return true;
                }
            });
        }
    }

    /**
     * Transform level location.
     *
     * @param location the location
     * @return the location
     */
    public static Location transformLevel(Location location) {
        int xDiff = 320;
        int yDiff = 1856;
        if (location.getRegionId() == 12105 && location.getZ() == 2) {
            return new Location(location.getX() + xDiff, location.getY() - yDiff, 3);
        }
        return location.transform(0, 0, -1);
    }

    private Location getGapLocation(Player player, Scenery object) {
        final Location[] data = getLocationData(GAP_LOCATIONS, object.getLocation());
        return getClosest(new Location[]{data[0], data[1]}, player.getLocation());
    }

    private Location getGapCrossLocation(Player player, Scenery object) {
        final Location[] data = getLocationData(GAP_CROSS_LOCATIONS, object.getLocation());
        return getClosest(new Location[]{data[4], data[5]}, player.getLocation());
    }

    private Location getLedgeLocation(final Player player, final Scenery object) {
        final Location[] data = getLocationData(LEDGE_LOCATIONS, object.getLocation());
        return getClosest(new Location[]{data[4], data[5]}, player.getLocation());
    }

    private Location getClosest(Location[] data, Location location) {
        Location closest = null;
        for (Location l : data) {
            if (closest == null || l.getDistance(location) < closest.getDistance(location)) {
                closest = l;
            }
        }
        return closest;
    }

    private Location[] getLocationData(Location[][] data, Location location) {
        for (Location[] datum : data) {
            for (Location l : datum) {
                if (l.equals(location)) {
                    return datum;
                }
            }
        }
        return null;
    }

    private boolean hasFailed(final Player player) {
        return hasFailed(player, 0.009000);
    }

    private boolean hasFailed(final Player player, double mod) {
        return AgilityHandler.hasFailed(player, 10, mod);
    }

    /**
     * Add config.
     *
     * @param player   the player
     * @param objectId the object id
     * @param value    the value
     * @param save     the save
     */
    public static void addConfig(final Player player, final int objectId, final int value, boolean save) {
        final VarbitDefinition definition = VarbitDefinition.forSceneryID(SceneryDefinition.forId(objectId).getVarbitID());
        final int oldVal = (definition.getValue(player) << definition.getStartBit());
        final int newVal = (value << definition.getStartBit());
        setVarp(player, CONFIG_ID, (getVarp(player, CONFIG_ID) - oldVal) + newVal, save);
    }

    /**
     * Add config.
     *
     * @param player the player
     * @param object the object
     * @param value  the value
     * @param save   the save
     */
    public static void addConfig(final Player player, final Scenery object, final int value, boolean save) {
        addConfig(player, object.getId(), value, save);
    }

    @Override
    public Location getDestination(Node node, Node n) {
        switch (n.getId()) {
            case 16535:
            case 16536:
                return n.getLocation().transform(node.getLocation().getX() < n.getLocation().getX() ? -1 : 1, 0, 0);
            case 10865:
                return null;
            case 10857:
                return n.getLocation().transform(0, -1, 0);
            case 10858:
                return n.getLocation().transform(0, 2, 0);
            case 10859:
                return getClosest(getLocationData(GAP_LOCATIONS, n.getLocation()), node.getLocation());
            case 10860:
            case 10886:
            case 10887:
            case 10888:
            case 10889:
                return getClosest(getLocationData(LEDGE_LOCATIONS, n.getLocation()), node.getLocation());
            case 10868:
            case 10867:
                return n.getLocation();
            case 10882:
            case 10883:
            case 10884:
            case 10885:
            case 10861:
            case 10862:
            case 10863:
            case 10864:
                if (n.getId() == 10863 && n.getLocation().equals(new Location(3372, 2832, 1))) {
                    return Location.create(3372, 2832, 1);
                }
                return getClosest(getLocationData(GAP_CROSS_LOCATIONS, n.getLocation()), node.getLocation());
        }
        return null;
    }

    @Override
    public AgilityCourse createInstance(Player player) {
        return new AgilityPyramidCourse(player);
    }

}
