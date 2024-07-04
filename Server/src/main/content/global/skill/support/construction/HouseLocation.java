package content.global.skill.support.construction;


import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.world.map.Location;
import core.tools.StringUtils;

/**
 * The house locations.
 */
@SuppressWarnings("all")
public enum HouseLocation {

    /**
     * Nowhere house location.
     */
    NOWHERE(-1, null, 0, 0),

    /**
     * Rimmington house location.
     */
    RIMMINGTON(15478, Location.create(2953, 3224, 0), 5000, 1),

    /**
     * Taverly house location.
     */
    TAVERLY(15477, Location.create(2893, 3465, 0), 5000, 10),

    /**
     * Pollnivneach house location.
     */
    POLLNIVNEACH(15479, Location.create(3340, 3003, 0), 7500, 20),

    /**
     * Rellekka house location.
     */
    RELLEKKA(15480, Location.create(2670, 3631, 0), 10000, 30),

    /**
     * Brimhaven house location.
     */
    BRIMHAVEN(15481, Location.create(2757, 3178, 0), 15000, 40),

    /**
     * Yanille house location.
     */
    YANILLE(15482, Location.create(2544, 3096, 0), 25000, 50);

    /**
     * The portal object id for this location.
     */
    private final int portalId;

    /**
     * The exit location.
     */
    private final Location exitLocation;

    /**
     * The cost to move
     */
    private final int cost;

    /**
     * Level requirement
     */
    private final int levelRequirement;

    /**
     * Checks if the player has the level.
     *
     * @param player the player.
     * @return {@code True} if so.
     */
    public boolean hasLevel(Player player) {
        return player.getSkills().getStaticLevel(Skills.CONSTRUCTION) >= levelRequirement;
    }

    /**
     * Constructs a new {@code HouseLocation} {@code Object}
     *
     * @param portalId     The portal id.
     * @param exitLocation The exit location.
     */
    private HouseLocation(int portalId, Location exitLocation, int cost, int levelRequirement) {
        this.portalId = portalId;
        this.exitLocation = exitLocation;
        this.cost = cost;
        this.levelRequirement = levelRequirement;
    }

    /**
     * Gets the name formatted.
     *
     * @return the formatted name.
     */
    public String getName() {
        return StringUtils.formatDisplayName(name().toLowerCase());
    }


    /**
     * Gets the portalId.
     *
     * @return the portalId
     */
    public int getPortalId() {
        return portalId;
    }

    /**
     * Gets the exitLocation.
     *
     * @return the exitLocation
     */
    public Location getExitLocation() {
        return exitLocation;
    }

    /**
     * Gets the cost to move to here
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the level requirement to move to here
     *
     * @return the level requirement
     */
    public int getLevelRequirement() {
        return levelRequirement;
    }


}
