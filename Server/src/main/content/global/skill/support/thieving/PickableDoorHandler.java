package content.global.skill.support.thieving;

import core.api.consts.Sounds;
import core.cache.def.impl.SceneryDefinition;
import core.game.global.action.DoorActionHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.ImpactHandler;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.tools.RandomFunction;

import java.util.ArrayList;
import java.util.List;

import static core.api.ContentAPIKt.playAudio;
import static core.api.ContentAPIKt.sendMessage;

@Initializable
public class PickableDoorHandler extends OptionHandler {
    private static final Item LOCK_PICK = new Item(1523);
    private static final List<PickableDoor> pickableDoors = new ArrayList<>(20);
    private static final int[] DOORS = new int[]{42028, 2550, 2551, 2554, 2555, 2556, 2557, 2558, 2559, 5501, 7246, 9565, 13314, 13317, 13320, 13323, 13326, 13344, 13345, 13346, 13347, 13348, 13349, 15759, 34005, 34805, 34806, 34812, 4799};

    PickableDoor door;

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (int i : DOORS) {
            SceneryDefinition.forId(i).getHandlers().put("option:pick-lock", this);
            SceneryDefinition.forId(i).getHandlers().put("option:open", this);
        }
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(2672, 3308, 0)}, 1, 3.8));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(2672, 3301, 0)}, 14, 15));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(2610, 3316, 0)}, 15, 15));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(3190, 3957, 0)}, 32, 25, true));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(2565, 3356, 0)}, 46, 37.5));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(2579, 3286, 1)}, 61, 50));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(2579, 3307, 1)}, 61, 50));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(3018, 3187, 0)}, 1, 0.0));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(2601, 9482, 0)}, 82, 0.0, true));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(3044, 3956, 0)}, 39, 35.0, true, true));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(3041, 3959, 0)}, 39, 35.0, true, true));
        pickableDoors.add(new PickableDoor(new Location[]{Location.create(3038, 3956, 0)}, 39, 35.0, true, true));
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        door = forDoor(node.getLocation());
        if (option.equals("open")) {
            if (door == null) {
                player.getPacketDispatch().sendMessage("The door is locked.");
                return true;
            }
            door.open(player, (Scenery) node);
            return true;
        }
        if (option.equals("pick-lock")) {
            if (door == null) {
                sendMessage(player, "This door cannot be unlocked.");
                return true;
            }
            playAudio(player, Sounds.PICK_LOCK_2407);
            door.pickLock(player, (Scenery) node);
            return true;
        }

        return false;
    }

    @Override
    public Location getDestination(Node node, Node n) {
        if (n instanceof Scenery) {
            Scenery object = (Scenery) n;
            if (object.getDefinition().hasAction("pick-lock")) {
                return DoorActionHandler.getDestination((Entity) node, object);
            }
        }
        return null;
    }

    private PickableDoor forDoor(Location location) {
        for (PickableDoor door : pickableDoors) {
            for (Location l : door.getLocations()) {
                if (l.equals(location)) {
                    return door;
                }
            }
        }
        return null;
    }

    public class PickableDoor {
        private final Location[] locations;
        private final int level;
        private final double experience;
        private final boolean lockpick;
        private final boolean flipped;

        public PickableDoor(final Location[] locations, int level, double experience, boolean lockpick, boolean flipped) {
            this.locations = locations;
            this.level = level;
            this.experience = experience;
            this.lockpick = lockpick;
            this.flipped = flipped;
        }

        public PickableDoor(final Location[] locations, int level, double experience, boolean lockpick) {
            this(locations, level, experience, lockpick, false);
        }

        public PickableDoor(Location[] locations, int level, double experience) {
            this(locations, level, experience, false);
        }

        public Location[] getLocations() {
            return locations;
        }

        public void open(Player player, Scenery object) {
            if (isInside(player, object) != flipped) {
                DoorActionHandler.handleAutowalkDoor(player, object);
                player.getPacketDispatch().sendMessage("You go through the door.");
            } else {
                player.getPacketDispatch().sendMessage("The door is locked.");
            }
        }

        public void pickLock(Player player, Scenery object) {
            boolean success = RandomFunction.random(12) >= 4;
            if (isInside(player, object) != flipped) {
                player.getPacketDispatch().sendMessage("The door is already unlocked.");
                return;
            }
            if (player.getSkills().getLevel(Skills.THIEVING) < level) {
                player.sendMessage("You attempt to pick the lock.");
                boolean hit = RandomFunction.random(10) < 5;
                player.getImpactHandler().manualHit(player, RandomFunction.random(1, 3), ImpactHandler.HitsplatType.NORMAL);
                player.sendMessage(hit ? "You have activated a trap on the lock." : "You fail to pick the lock.");
                return;
            }
            if (lockpick && !player.getInventory().containsItem(LOCK_PICK)) {
                player.sendMessage("You need a lockpick in order to pick this lock.");
                return;
            }
            if (success) {
                player.getSkills().addExperience(Skills.THIEVING, experience, true);
                DoorActionHandler.handleAutowalkDoor(player, object);
            }
            player.getPacketDispatch().sendMessage("You attempt to pick the lock.");
            player.getPacketDispatch().sendMessage("You " + (success ? "manage" : "fail") + " to pick the lock.");
        }

        private boolean isInside(Player player, Scenery object) {
            boolean inside = false;
            Direction dir = Direction.getLogicalDirection(player.getLocation(), object.getLocation());
            Direction direction = object.getDirection();
            if (direction == Direction.SOUTH && dir == Direction.WEST) {
                inside = true;
            } else if (direction == Direction.EAST && dir == Direction.SOUTH) {
                inside = true;
            } else if (direction == Direction.NORTH && dir == Direction.EAST) {
                inside = true;
            }
            return inside;
        }

        public int getLevel() {
            return level;
        }
        public double getExperience() {
            return experience;
        }
        public boolean isLockpick() {
            return lockpick;
        }
    }

}
