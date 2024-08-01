package core.game.world.update.flag.context;

import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.chunk.GraphicUpdateFlag;

public class Graphic {

    private final int id;

    private final int height;

    private final int delay;

    public Graphic(int id) {
        this(id, 0, 0);
    }

    public Graphic(int id, int height) {
        this(id, height, 0);
    }

    public Graphic(int id, int height, int delay) {
        this.id = id;
        this.height = height;
        this.delay = delay;
    }

    public static Graphic create(int id) {
        return new Graphic(id, 0, 0);
    }

    public static Graphic create(int id, int height) {
        return new Graphic(id, height, 0);
    }

    public static void send(Graphic graphic, Location l) {
        RegionManager.getRegionChunk(l).flag(new GraphicUpdateFlag(graphic, l));
    }

    public int getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "Graphic [id=" + id + ", height=" + height + ", delay=" + delay + "]";
    }
}