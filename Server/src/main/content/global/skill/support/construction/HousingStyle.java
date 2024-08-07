package content.global.skill.support.construction;

import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;

/**
 * The enum Housing style.
 */
public enum HousingStyle {

    /**
     * Basic wood housing style.
     */
    BASIC_WOOD(1, 5000, 7503, 0, 13100, 13101, 13098, Decoration.BASIC_WOOD_WINDOW),
    /**
     * Basic stone housing style.
     */
    BASIC_STONE(10, 5000, 7503, 1, 13094, 13096, 1902, Decoration.BASIC_STONE_WINDOW),
    /**
     * Whitewashed stone housing style.
     */
    WHITEWASHED_STONE(20, 7500, 7503, 2, 13006, 13007, 1415, Decoration.WHITEWASHED_STONE_WINDOW),
    /**
     * Fremennik style wood housing style.
     */
    FREMENNIK_STYLE_WOOD(30, 10000, 7503, 3, 13109, 13107, 13111, Decoration.FREMENNIK_WINDOW),
    /**
     * Tropical wood housing style.
     */
    TROPICAL_WOOD(40, 15000, 7759, 0, 13016, 13015, 13011, Decoration.TROPICAL_WOOD_WINDOW),
    /**
     * Fancy stone housing style.
     */
    FANCY_STONE(50, 25000, 7759, 1, 13119, 13118, 13116, Decoration.FANCY_STONE_WINDOW);

    private final int levelRequirement;

    private final int cost;

    private final int regionId;

    private final int plane;

    private final int doorId;

    private final int secondDoorId;

    private final int wallId;

    private final Decoration window;

    /**
     * Has level boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public boolean hasLevel(Player player) {
        return player.getSkills().getStaticLevel(Skills.CONSTRUCTION) >= levelRequirement;
    }

    private HousingStyle(int level, int cost, int regionId, int plane, int doorId, int secondDoorId, int wallId, Decoration window) {
        this.levelRequirement = level;
        this.cost = cost;
        this.regionId = regionId;
        this.plane = plane;
        this.doorId = doorId;
        this.secondDoorId = secondDoorId;
        this.wallId = wallId;
        this.window = window;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return levelRequirement;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets region id.
     *
     * @return the region id
     */
    public int getRegionId() {
        return regionId;
    }

    /**
     * Gets plane.
     *
     * @return the plane
     */
    public int getPlane() {
        return plane;
    }

    /**
     * Gets door id.
     *
     * @return the door id
     */
    public int getDoorId() {
        return doorId;
    }

    /**
     * Gets wall id.
     *
     * @return the wall id
     */
    public int getWallId() {
        return wallId;
    }

    /**
     * Gets window style.
     *
     * @return the window style
     */
    public Decoration getWindowStyle() {
        return window;
    }

    /**
     * Gets second door id.
     *
     * @return the second door id
     */
    public int getSecondDoorId() {
        return secondDoorId;
    }
}