package core.game.world.update.flag.context;

import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.chunk.GraphicUpdateFlag;

/**
 * Represents a graphic.
 * @author Emperor
 */
public class Graphic {

    /**
     * The graphic id.
     */
    private final int id;

    /**
     * The graphic height.
     */
    private final int height;

    /**
     * The graphic height.
     */
    private final int delay;

    /**
     * Constructs a new {@code Graphics} {@code Object}.
     *
     * @param id The graphics id.
     */
    public Graphic(int id) {
        this(id, 0, 0);
    }

    /**
     * Constructs a new {@code Graphic} {@code Object}.
     *
     * @param id     The graphics id.
     * @param height The graphics height.
     */
    public Graphic(int id, int height) {
        this(id, height, 0);
    }

    /**
     * Constructs a new {@code Graphic} {@code Object}.
     *
     * @param id     The graphics id.
     * @param height The graphics height.
     * @param delay  The graphics delay.
     */
    public Graphic(int id, int height, int delay) {
        this.id = id;
        this.height = height;
        this.delay = delay;
    }

    /**
     * Constructs a new graphic.
     *
     * @param id The graphic id.
     * @return The graphic instance.
     */
    public static Graphic create(int id) {
        return new Graphic(id, 0, 0);
    }

    /**
     * Constructs a new graphic.
     *
     * @param id     The graphic id.
     * @param height The graphic height.
     * @return The graphic instance.
     */
    public static Graphic create(int id, int height) {
        return new Graphic(id, height, 0);
    }

    /**
     * Sends a graphic on a location.
     *
     * @param graphic The graphic.
     * @param l       The location.
     */
    public static void send(Graphic graphic, Location l) {
        RegionManager.getRegionChunk(l).flag(new GraphicUpdateFlag(graphic, l));
    }

    /**
     * Get the graphic id.
     *
     * @return The graphic id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the graphic height.
     *
     * @return The graphic height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the delay.
     *
     * @return The delay.
     */
    public int getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "Graphic [id=" + id + ", height=" + height + ", delay=" + delay + "]";
    }
}