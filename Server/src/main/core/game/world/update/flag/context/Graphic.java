package core.game.world.update.flag.context;

import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.chunk.GraphicUpdateFlag;

/**
 * Graphic.
 */
public class Graphic {

    private final int id;

    private final int height;

    private final int delay;

    /**
     * Instantiates a new Graphic.
     *
     * @param id the id
     */
    public Graphic(int id) {
        this(id, 0, 0);
    }

    /**
     * Instantiates a new Graphic.
     *
     * @param id     the id
     * @param height the height
     */
    public Graphic(int id, int height) {
        this(id, height, 0);
    }

    /**
     * Instantiates a new Graphic.
     *
     * @param id     the id
     * @param height the height
     * @param delay  the delay
     */
    public Graphic(int id, int height, int delay) {
        this.id = id;
        this.height = height;
        this.delay = delay;
    }

    /**
     * Create graphic.
     *
     * @param id the id
     * @return the graphic
     */
    public static Graphic create(int id) {
        return new Graphic(id, 0, 0);
    }

    /**
     * Create graphic.
     *
     * @param id     the id
     * @param height the height
     * @return the graphic
     */
    public static Graphic create(int id, int height) {
        return new Graphic(id, height, 0);
    }

    /**
     * Send.
     *
     * @param graphic the graphic
     * @param l       the l
     */
    public static void send(Graphic graphic, Location l) {
        RegionManager.getRegionChunk(l).flag(new GraphicUpdateFlag(graphic, l));
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets delay.
     *
     * @return the delay
     */
    public int getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "Graphic [id=" + id + ", height=" + height + ", delay=" + delay + "]";
    }
}