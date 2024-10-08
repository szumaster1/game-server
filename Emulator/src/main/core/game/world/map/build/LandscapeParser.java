package core.game.world.map.build;

import core.cache.def.impl.SceneryDefinition;
import core.cache.misc.buffer.ByteBufferUtils;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.game.world.map.Region;
import core.game.world.map.RegionManager;
import core.game.world.map.RegionPlane;

import java.nio.ByteBuffer;

/**
 * A utility class used for parsing landscapes.
 * @author Emperor
 */
public final class LandscapeParser {

    /**
     * Parses the landscape.
     *
     * @param r            The region.
     * @param mapscape     The mapscape data.
     * @param buffer       the buffer
     * @param storeObjects If all objects should be stored (rather than just the objects with options).
     */
    public static void parse(Region r, byte[][][] mapscape, ByteBuffer buffer, boolean storeObjects) {
        int objectId = -1;
        for (; ; ) {
            int offset = ByteBufferUtils.getBigSmart(buffer);
            if (offset == 0) {
                break;
            }
            objectId += offset;
            int location = 0;
            for (; ; ) {
                offset = ByteBufferUtils.getSmart(buffer);
                if (offset == 0) {
                    break;
                }
                location += offset - 1;
                int y = location & 0x3f;
                int x = location >> 6 & 0x3f;
                int configuration = buffer.get() & 0xFF;
                int rotation = configuration & 0x3;
                int type = configuration >> 2;
                int z = location >> 12;
                r.setObjectCount(r.getObjectCount() + 1);
                if (x < 0 || y < 0 || x >= 64 || y >= 64) {

                } else {
                    if ((mapscape[1][x][y] & 0x2) == 2) {
                        z--;
                    }
                    if (z >= 0 && z <= 3) {
                        Scenery object = new Scenery(objectId, Location.create((r.getX() << 6) + x, (r.getY() << 6) + y, z), type, rotation);
                        flagScenery(r.getPlanes()[z], x, y, object, true, storeObjects);
                    }
                }
            }
        }
    }

    /**
     * Adds a scenery temporarily.
     *
     * @param object The object to add.
     */
    public static void addScenery(Scenery object) {
        addScenery(object, false);
    }

    /**
     * Adds a scenery.
     *
     * @param object    The object to add.
     * @param landscape If the object should be added permanent.
     */
    public static void addScenery(Scenery object, boolean landscape) {
        Location l = object.getLocation();
        flagScenery(RegionManager.getRegionPlane(l), l.getLocalX(), l.getLocalY(), object, landscape, false);
    }

    /**
     * Flags a scenery on the plane's clipping flags.
     *
     * @param plane        The plane.
     * @param localX       the local x
     * @param localY       the local y
     * @param object       The object.
     * @param landscape    If we are adding this scenery permanent.
     * @param storeObjects If all objects should be stored (rather than just the objects with options).
     */
    public static void flagScenery(RegionPlane plane, int localX, int localY, Scenery object, boolean landscape, boolean storeObjects) {
        Region.load(plane.getRegion());
        SceneryDefinition def = object.getDefinition();
        object.setActive(true);
        boolean add = storeObjects || !landscape || def.getChildObject(null).hasActions();
        if (add) {
            addPlaneObject(plane, object, localX, localY, landscape, storeObjects);
        }

        if (!applyClippingFlagsFor(plane, localX, localY, object))
            return;

        if (!storeObjects && !add && (!def.getChildObject(null).getName().equals("null"))) {
            addPlaneObject(plane, object, localX, localY, landscape, false);
        }
    }

    /**
     * Apply clipping flags for boolean.
     *
     * @param plane  the plane
     * @param localX the local x
     * @param localY the local y
     * @param object the object
     * @return the boolean
     */
    public static boolean applyClippingFlagsFor(RegionPlane plane, int localX, int localY, Scenery object) {
        SceneryDefinition def = object.getDefinition();
        int sizeX;
        int sizeY;
        if (object.getRotation() % 2 == 0) {
            sizeX = def.sizeX;
            sizeY = def.sizeY;
        } else {
            sizeX = def.sizeY;
            sizeY = def.sizeX;
        }
        int type = object.getType();
        if (type == 22) { //Tile
            plane.getFlags().getLandscape()[localX][localY] = true;
            if (def.interactable != 0 || def.clipType == 1 || def.secondBool) {
                if (def.clipType == 1) {
                    plane.getFlags().flagTileObject(localX, localY);
                    if (def.isProjectileClipped()) {
                        plane.getProjectileFlags().flagTileObject(localX, localY);
                    }
                }
            }
        } else if (type >= 9) { //Default objects
            if (def.clipType != 0) {
                plane.getFlags().flagSolidObject(localX, localY, sizeX, sizeY, def.projectileClipped);
                if (def.isProjectileClipped()) {
                    plane.getProjectileFlags().flagSolidObject(localX, localY, sizeX, sizeY, def.projectileClipped);
                }
            }
        } else if (type >= 0 && type <= 3) { //Doors/walls
            if (def.clipType != 0) {
                plane.getFlags().flagDoorObject(localX, localY, object.getRotation(), type, def.projectileClipped);
                if (def.isProjectileClipped()) {
                    plane.getProjectileFlags().flagDoorObject(localX, localY, object.getRotation(), type, def.projectileClipped);
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Adds an object to the region plane.
     *
     * @param plane     The region plane.
     * @param object    The object to add.
     * @param localX    The local x-coordinate.
     * @param localY    The local y-coordinate.
     * @param landscape The landscape.
     */
    private static void addPlaneObject(RegionPlane plane, Scenery object, int localX, int localY, boolean landscape, boolean storeAll) {
        if (landscape && !storeAll) {
            Scenery current = plane.getObjects()[localX][localY];
            if (current != null && current.getDefinition().getChildObject(null).hasOptions(!object.getDefinition().getChildObject(null).hasOptions(false))) {
                return;
            }
        }
        plane.add(object, localX, localY, landscape && !storeAll);
    }

    /**
     * Removes a scenery.
     *
     * @param object The object.
     * @return The removed scenery.
     */
    public static Scenery removeScenery(Scenery object) {
        if (!object.isRenderable()) {
            return null;
        }
        RegionPlane plane = RegionManager.getRegionPlane(object.getLocation());
        Region.load(plane.getRegion());
        int localX = object.getLocation().getLocalX();
        int localY = object.getLocation().getLocalY();
        Scenery current = plane.getChunkObject(localX, localY, object.getId());
        if (current == null || current.getId() != object.getId()) {
            return null;
        }
        current.setActive(false);
        object.setActive(false);
        plane.remove(localX, localY, object.getId());
        SceneryDefinition def = object.getDefinition();
        int sizeX;
        int sizeY;
        if (object.getRotation() % 2 == 0) {
            sizeX = def.sizeX;
            sizeY = def.sizeY;
        } else {
            sizeX = def.sizeY;
            sizeY = def.sizeX;
        }
        int type = object.getType();
        if (type == 22) { //Tile
            if (def.interactable != 0 || def.clipType == 1 || def.secondBool) {
                if (def.clipType == 1) {
                    plane.getFlags().unflagTileObject(localX, localY);
                    if (def.isProjectileClipped()) {
                        plane.getProjectileFlags().unflagTileObject(localX, localY);
                    }
                }
            }
        } else if (type >= 9) { //Default objects
            if (def.clipType != 0) {
                plane.getFlags().unflagSolidObject(localX, localY, sizeX, sizeY, def.projectileClipped);
                if (def.isProjectileClipped()) {
                    plane.getProjectileFlags().unflagSolidObject(localX, localY, sizeX, sizeY, def.projectileClipped);
                }
            }
        } else if (type >= 0 && type <= 3) { //Doors/walls
            if (def.clipType != 0) {
                plane.getFlags().unflagDoorObject(localX, localY, object.getRotation(), type, def.projectileClipped);
                if (def.isProjectileClipped()) {
                    plane.getProjectileFlags().unflagDoorObject(localX, localY, object.getRotation(), type, def.projectileClipped);
                }
            }
        }
        return current;
    }
}
