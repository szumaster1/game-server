package core.game.interaction;

import core.game.global.action.DoorActionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.scenery.Scenery;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;

/**
 * Holds the destination flags for all types of nodes.
 * @author Emperor
 */
public class DestinationFlag {

    /**
     * The Location destination flag.
     */
    public static final DestinationFlag LOCATION = new DestinationFlag();

    /**
     * The entity destination flag.
     */
    public static final DestinationFlag ENTITY = new DestinationFlag() {

        @Override
        public Location getDestination(Entity mover, Node n) {
            // Get the closest location to the mover, considering the node's location
            Location l = getClosestTo(mover, n, n.getLocation().transform(0, -1, 0));
            // Check if the mover has a size greater than 1
            if (mover.size() > 1) {
                // Adjust the location based on the mover's size in the X direction
                if (l.getX() < n.getLocation().getX()) {
                    l = l.transform(-(mover.size() - 1), 0, 0);
                }
                // Adjust the location based on the mover's size in the Y direction
                if (l.getY() < n.getLocation().getY()) {
                    l = l.transform(0, -(mover.size() - 1), 0);
                }
            }
            return l; // Return the adjusted location
        }
    };

    /**
     * The following an entity destination flag.
     */
    public static final DestinationFlag FOLLOW_ENTITY = new DestinationFlag() {

        @Override
        public Location getDestination(Entity mover, Node n) {
            // Get the direction of the node
            Direction dir = n.getDirection();
            // Transform the node's location based on the direction
            Location l = n.getLocation().transform(-dir.getStepX(), -dir.getStepY(), 0);
            // Check if traversal is permitted to the new location
            if (!checkTraversal(l, dir)) {
                l = getClosestTo(mover, n, l); // Get the closest location if traversal is not permitted
            }
            // Check if the mover has a size greater than 1
            if (mover.size() > 1) {
                // Adjust the location based on the mover's size in the X direction
                if (l.getX() < n.getLocation().getX()) {
                    l = l.transform(-(mover.size() - 1), 0, 0);
                }
                // Adjust the location based on the mover's size in the Y direction
                if (l.getY() < n.getLocation().getY()) {
                    l = l.transform(0, -(mover.size() - 1), 0);
                }
            }
            return l; // Return the adjusted location
        }
    };

    /**
     * The entity destination flag.
     */
    public static final DestinationFlag COMBAT = new DestinationFlag() {

        @Override
        public Location getDestination(Entity mover, Node n) {
            return null; // Placeholder for combat destination logic
        }
    };

    /**
     * The item destination flag.
     */
    public static final DestinationFlag ITEM = new DestinationFlag() {

        @Override
        public Location getDestination(Entity mover, Node n) {
            // Check if teleportation is permitted at the node's location
            if (!RegionManager.isTeleportPermitted(n.getLocation())) {
                return getClosestTo(mover, n, n.getLocation().transform(1, 0, 0)); // Get closest location if not permitted
            }
            return n.getLocation(); // Return the node's location if teleportation is permitted
        }

    };

    /**
     * The object destination flag.
     */
    public static final DestinationFlag OBJECT = new DestinationFlag() {

        @Override
        public Location getDestination(Entity mover, Node n) {
            Scenery object = (Scenery) n; // Cast node to Scenery type
            // Check the type of the object to determine the destination logic
            if (object.getType() < 4 || object.getType() == 9) {
                return DoorActionHandler.getDestination(mover, object); // Handle door action
            }
            if (object.getType() == 4 || object.getType() == 5) { // Wall or decoration
                return object.getLocation(); // Return the object's location
            }
            int sizeX = object.getDefinition().sizeX; // Get object's width
            int sizeY = object.getDefinition().sizeY; // Get object's height
            // Adjust size based on object's rotation
            if (object.getRotation() % 2 != 0) {
                int switcher = sizeX;
                sizeX = sizeY;
                sizeY = switcher;
            }
            Direction dir = Direction.forWalkFlag(object.getDefinition().getWalkingFlag(), object.getRotation()); // Get direction based on walking flag
            if (dir != null) {
                return getDestination(mover, object, sizeX, sizeY, dir, 3); // Get destination based on direction
            }

            return getDestination(mover, object, sizeX, sizeY, Direction.getLogicalDirection(object.getLocation(), mover.getLocation()), 0); // Fallback to logical direction
        }

        /**
         * Gets the destination for the given object.
         * @param object The object.
         * @param dir The preferred direction from the object.
         * @return The teleporting destination.
         */
        private Location getDestination(Entity mover, Scenery object, int sizeX, int sizeY, Direction dir, int count) {
            Location closest = null; // Initialize closest location
            double distance = 9999.9; // Initialize distance
            Location loc = object.getLocation(); // Get the object's location
            for (int i = count; i < 4; i++) {
                // Check if the direction is odd to determine traversal
                if (dir.toInteger() % 2 != 0) {
                    int x = dir.getStepX();
                    if (x > 0) {
                        x *= sizeX; // Scale X based on size
                    }
                    for (int y = 0; y < sizeY; y++) {
                        Location l = loc.transform(x, y, 0); // Transform location
                        if (checkTraversal(l, dir)) { // Check if traversal is permitted
                            double dist = mover.getLocation().getDistance(l); // Calculate distance
                            if (dist < distance) { // Update closest location if a shorter distance is found
                                distance = dist;
                                closest = l;
                            }
                        }
                    }
                } else {
                    int y = dir.getStepY();
                    if (y > 0) {
                        y *= sizeY; // Scale Y based on size
                    }
                    for (int x = 0; x < sizeX; x++) {
                        Location l = loc.transform(x, y, 0); // Transform location
                        if (checkTraversal(l, dir)) { // Check if traversal is permitted
                            double dist = mover.getLocation().getDistance(l); // Calculate distance
                            if (dist < distance) { // Update closest location if a shorter distance is found
                                distance = dist;
                                closest = l;
                            }
                        }
                    }
                }
                dir = Direction.get((dir.toInteger() + 1) % 4); // Rotate direction
            }
            return closest; // Return the closest location found
        }

        @Override
        public boolean checkTraversal(Location l, Direction dir) {
            // Check if teleportation is permitted and if the direction can move to the location
            return RegionManager.isTeleportPermitted(l) && dir.canMove(l);
        }
    };

    /**
     * Constructs a new {@code DestinationFlag} {@code Object}.
     */
    public DestinationFlag() {
        /*
         * empty.
         */
    }

    /**
     * Gets the default destination location.
     * @param mover The moving entity.
     * @param node The node to move to.
     * @return The location to walk to.
     */
    public Location getDestination(Entity mover, Node node) {
        return node.getLocation(); // Return the node's location as the default destination
    }

    /**
     * Checks if traversal is permitted.
     * @param l The location to check.
     * @param dir The direction to move.
     * @return {@code True}.
     */
    public boolean checkTraversal(Location l, Direction dir) {
        // Check if the opposite direction can move to the location
        return Direction.get((dir.toInteger() + 2) % 4).canMove(l);
    }

    /**
     * Gets the closest destination to the current destination, to reach the
     * node.
     * @param mover The moving entity.
     * @param node The node to move to.
     * @param suggestion The suggested destination location.
     * @return The destination location.
     */
    public Location getClosestTo(Entity mover, Node node, Location suggestion) {
        Location nl = node.getLocation(); // Get the node's location
        int diffX = suggestion.getX() - nl.getX(); // Calculate X difference
        int diffY = suggestion.getY() - nl.getY(); // Calculate Y difference
        Direction moveDir = Direction.NORTH; // Initialize movement direction
        if (diffX < 0) {
            moveDir = Direction.EAST; // Set direction based on X difference
        } else if (diffX >= node.size()) {
            moveDir = Direction.WEST; // Set direction based on X difference
        } else if (diffY >= node.size()) {
            moveDir = Direction.SOUTH; // Set direction based on Y difference
        }
        double distance = 9999.9; // Initialize distance
        Location destination = suggestion; // Set initial destination
        for (int c = 0; c < 4; c++) {
            for (int i = 0; i < node.size() + 1; i++) {
                for (int j = 0; j < (i == 0 ? 1 : 2); j++) {
                    Direction current = Direction.get((moveDir.toInteger() + (j == 1 ? 3 : 1)) % 4); // Get current direction
                    Location loc = suggestion.transform(current.getStepX() * i, current.getStepY() * i, 0); // Transform suggestion
                    // Check if the location is within bounds based on direction
                    if (moveDir.toInteger() % 2 == 0) {
                        if (loc.getX() < nl.getX() || loc.getX() > nl.getX() + node.size() - 1) {
                            continue; // Skip if out of bounds
                        }
                    } else {
                        if (loc.getY() < nl.getY() || loc.getY() > nl.getY() + node.size() - 1) {
                            continue; // Skip if out of bounds
                        }
                    }
                    if (checkTraversal(loc, moveDir)) { // Check if traversal is permitted
                        double dist = mover.getLocation().getDistance(loc); // Calculate distance
                        if (dist < distance) { // Update closest location if a shorter distance is found
                            distance = dist;
                            destination = loc; // Update destination
                        }
                    }
                }
            }
            moveDir = Direction.get((moveDir.toInteger() + 1) % 4); // Rotate direction
            int offsetX = Math.abs(moveDir.getStepY() * (node.size() >> 1)); // Calculate X offset
            int offsetY = Math.abs(moveDir.getStepX() * (node.size() >> 1)); // Calculate Y offset
            // Adjust suggestion based on direction
            if (moveDir.toInteger() < 2) {
                suggestion = node.getLocation().transform(-moveDir.getStepX() + offsetX, -moveDir.getStepY() + offsetY, 0);
            } else {
                suggestion = node.getLocation().transform(-moveDir.getStepX() * node.size() + offsetX, -moveDir.getStepY() * node.size() + offsetY, 0);
            }
        }
        return destination; // Return the closest destination found
    }

}
