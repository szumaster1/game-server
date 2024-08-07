package core.game.world.map.zone;

import core.game.node.Node;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.tools.RandomFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Zone borders.
 */
public final class ZoneBorders {

    private final int southWestX;

    private final int southWestY;

    private final int northEastX;

    private final int northEastY;

    private int plane = 0;

    private List<ZoneBorders> exceptions;

    private boolean zeroPlaneCheck;

    /**
     * Instantiates a new Zone borders.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     */
    public ZoneBorders(int x1, int y1, int x2, int y2) {
        this.southWestX = Math.min(x1, x2);
        this.southWestY = Math.min(y1, y2);
        this.northEastX = Math.max(x1, x2);
        this.northEastY = Math.max(y1, y2);
    }

    /**
     * Instantiates a new Zone borders.
     *
     * @param x1    the x 1
     * @param y1    the y 1
     * @param x2    the x 2
     * @param y2    the y 2
     * @param plane the plane
     */
    public ZoneBorders(int x1, int y1, int x2, int y2, int plane) {
        this.southWestX = Math.min(x1, x2);
        this.southWestY = Math.min(y1, y2);
        this.northEastX = Math.max(x1, x2);
        this.northEastY = Math.max(y1, y2);
        this.setPlane(plane);
    }

    /**
     * Instantiates a new Zone borders.
     *
     * @param x1             the x 1
     * @param y1             the y 1
     * @param x2             the x 2
     * @param y2             the y 2
     * @param plane          the plane
     * @param zeroPlaneCheck the zero plane check
     */
    public ZoneBorders(int x1, int y1, int x2, int y2, int plane, boolean zeroPlaneCheck) {
        this(x1, y1, x2, y2, plane);
        this.zeroPlaneCheck = zeroPlaneCheck;
    }

    /**
     * Instantiates a new Zone borders.
     *
     * @param l1 the l 1
     * @param l2 the l 2
     */
    public ZoneBorders(Location l1, Location l2) {
        this(l1.getX(), l1.getY(), l2.getX(), l2.getY(), l1.getZ());
    }

    /**
     * For region zone borders.
     *
     * @param regionId the region id
     * @return the zone borders
     */
    public static ZoneBorders forRegion(int regionId) {
        int baseX = ((regionId >> 8) & 0xFF) << 6;
        int baseY = (regionId & 0xFF) << 6;
        int size = 64 - 1;
        return new ZoneBorders(baseX, baseY, baseX + size, baseY + size);
    }

    /**
     * Inside border boolean.
     *
     * @param location the location
     * @return the boolean
     */
    public boolean insideBorder(Location location) {
        return insideBorder(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Inside border boolean.
     *
     * @param node the node
     * @return the boolean
     */
    public boolean insideBorder(Node node) {
        return insideBorder(node.getLocation());
    }

    /**
     * Inside border boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public boolean insideBorder(int x, int y) {
        return insideBorder(x, y, 0);
    }

    /**
     * Inside border boolean.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the boolean
     */
    public boolean insideBorder(int x, int y, int z) {
        if (zeroPlaneCheck ? z != plane : (plane != 0 && z != plane)) {
            return false;
        }
        if (southWestX <= x && southWestY <= y && northEastX >= x && northEastY >= y) {
            if (exceptions != null) {
                Object[] exceptArray = exceptions.toArray();
                int length = exceptArray.length;
                for (int i = 0; i < length; i++) {
                    ZoneBorders exception = (ZoneBorders) exceptArray[i];
                    if (exception.insideBorder(x, y, z)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Gets region ids.
     *
     * @return the region ids
     */
    public List<Integer> getRegionIds() {
        List<Integer> regionIds = new ArrayList<>(20);
        int neX = (northEastX >> 6) + 1;
        int neY = (northEastY >> 6) + 1;
        for (int x = southWestX >> 6; x < neX; x++) {
            for (int y = southWestY >> 6; y < neY; y++) {
                int id = y | x << 8;
                regionIds.add(id);
            }
        }
        return regionIds;
    }

    /**
     * Gets south west x.
     *
     * @return the south west x
     */
    public int getSouthWestX() {
        return southWestX;
    }

    /**
     * Gets south west y.
     *
     * @return the south west y
     */
    public int getSouthWestY() {
        return southWestY;
    }

    /**
     * Gets north east x.
     *
     * @return the north east x
     */
    public int getNorthEastX() {
        return northEastX;
    }

    /**
     * Gets north east y.
     *
     * @return the north east y
     */
    public int getNorthEastY() {
        return northEastY;
    }

    /**
     * Gets exceptions.
     *
     * @return the exceptions
     */
    public List<ZoneBorders> getExceptions() {
        return exceptions;
    }

    /**
     * Gets weighted random loc.
     *
     * @param intensity the intensity
     * @return the weighted random loc
     */
    public Location getWeightedRandomLoc(int intensity) {
        int x = northEastX - southWestX == 0 ? southWestX : RandomFunction.normalRandDist(northEastX - southWestX, intensity) + southWestX;
        int y = northEastY - southWestY == 0 ? southWestY : RandomFunction.normalRandDist(northEastY - southWestY, intensity) + southWestY;
        return new Location(x, y);
    }

    /**
     * Gets random loc.
     *
     * @return the random loc
     */
    public Location getRandomLoc() {
        int x = northEastX - southWestX == 0 ? southWestX : new Random().nextInt(northEastX - southWestX + 1) + southWestX;
        int y = northEastY - southWestY == 0 ? southWestY : new Random().nextInt(northEastY - southWestY + 1) + southWestY;
        return new Location(x, y, plane);
    }

    /**
     * Gets random walkable loc.
     *
     * @return the random walkable loc
     */
    public Location getRandomWalkableLoc() {
        Location loc = getRandomLoc();
        int tries = 0; // prevent bad code from DOSing server
        while (!RegionManager.isTeleportPermitted(loc) && tries < 20) {
            loc = getRandomLoc();
            tries += 1;
        }
        return loc;
    }

    /**
     * Add exception.
     *
     * @param exception the exception
     */
    public void addException(ZoneBorders exception) {
        if (exceptions == null) {
            this.exceptions = new ArrayList<>(20);
        }
        exceptions.add(exception);
    }

    @Override
    public String toString() {
        return "ZoneBorders [southWestX=" + southWestX + ", southWestY=" + southWestY + ", northEastX=" + northEastX + ", northEastY=" + northEastY + ", exceptions=" + exceptions + "]";
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
     * Sets plane.
     *
     * @param plane the plane
     */
    public void setPlane(int plane) {
        this.plane = plane;
    }

    /**
     * Inside region boolean.
     *
     * @param n the n
     * @return the boolean
     */
    public boolean insideRegion(Node n) {
        return insideBorder(n.getLocation().getRegionX(), n.getLocation().getRegionY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZoneBorders that = (ZoneBorders) o;
        return southWestX == that.southWestX && southWestY == that.southWestY && northEastX == that.northEastX && northEastY == that.northEastY && plane == that.plane && zeroPlaneCheck == that.zeroPlaneCheck && Objects.equals(exceptions, that.exceptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(southWestX, southWestY, northEastX, northEastY, plane, exceptions, zeroPlaneCheck);
    }
}
