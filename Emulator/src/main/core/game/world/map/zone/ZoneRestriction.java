package core.game.world.map.zone;

/**
 * The zone restrictions.
 * @author Emperor
 */
public enum ZoneRestriction {

    /**
     * No followers allowed in this zone.
     */
    FOLLOWERS,

    /**
     * No random events allowed.
     */
    RANDOM_EVENTS,

    /**
     * No fires allowed.
     */
    FIRES,

    /**
     * Members only.
     */
    MEMBERS,

    /**
     * No cannons allowed.
     */
    CANNON,
    /**
     * Do not spawn a grave if a player dies here
     */
    GRAVES,

    /**
     * No teleporting allowed.
     */
    TELEPORT,
    ;

    /**
     * Gets the restriction flag.
     *
     * @return The flag.
     */
    public int getFlag() {
        return 1 << ordinal();
    }
}