package content.global.skill.gathering.hunter;

import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.tools.RandomFunction;

/**
 * The Trap hook.
 */
public class TrapHook {

    private final TrapWrapper wrapper;

    private final Location[] locations;

    /**
     * Instantiates a new Trap hook.
     *
     * @param wrapper   the wrapper
     * @param locations the locations
     */
    public TrapHook(TrapWrapper wrapper, Location[] locations) {
        this.wrapper = wrapper;
        this.locations = locations;
    }

    /**
     * Gets chance location.
     *
     * @return the chance location
     */
    public Location getChanceLocation() {
        final double chance = wrapper.getChanceRate();
        final int roll = RandomFunction.random(99);
        final double successChance = (GameWorld.getSettings().isDevMode() ? 100 : 55.0) + chance;
        if (successChance > roll) {
            return RandomFunction.getRandomElement(locations);
        }
        return null;
    }

    /**
     * Is hooked boolean.
     *
     * @param location the location
     * @return the boolean
     */
    public boolean isHooked(Location location) {
        for (Location l : locations) {
            if (l.equals(location)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets wrapper.
     *
     * @return the wrapper
     */
    public TrapWrapper getWrapper() {
        return wrapper;
    }

    /**
     * Get locations location [ ].
     *
     * @return the location [ ]
     */
    public Location[] getLocations() {
        return locations;
    }

}
