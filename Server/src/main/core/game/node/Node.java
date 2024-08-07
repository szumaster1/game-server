package core.game.node;

import core.api.utils.Vector;
import core.game.interaction.DestinationFlag;
import core.game.interaction.InteractPlugin;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.tools.StringUtils;

/**
 * Node.
 */
public abstract class Node {

    /**
     * The Name.
     */
    protected String name;
    /**
     * The Location.
     */
    protected Location location;
    /**
     * The Index.
     */
    protected int index;
    /**
     * The Direction.
     */
    protected Direction direction;
    /**
     * The Size.
     */
    protected int size = 1;
    /**
     * The Active.
     */
    protected boolean active = true;
    /**
     * The Interact plugin.
     */
    protected InteractPlugin interactPlugin;
    /**
     * The Destination flag.
     */
    protected DestinationFlag destinationFlag;
    /**
     * The Renderable.
     */
    protected boolean renderable = true;

    /**
     * Instantiates a new Node.
     *
     * @param name     the name
     * @param location the location
     */
    public Node(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    /**
     * As npc npc.
     *
     * @return the npc
     */
    public NPC asNpc() {
        return (NPC) this;
    }

    /**
     * As player player.
     *
     * @return the player
     */
    public Player asPlayer() {
        return (Player) this;
    }

    /**
     * As scenery scenery.
     *
     * @return the scenery
     */
    public Scenery asScenery() {
        return (Scenery) this;
    }

    /**
     * As item item.
     *
     * @return the item
     */
    public Item asItem() {
        return (Item) this;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return this instanceof NPC ? ((NPC) this).getId() : this instanceof Scenery ? ((Scenery) this).getId() : this instanceof Item ? ((Item) this).getId() : -1;
    }

    /**
     * Gets id hash.
     *
     * @return the id hash
     */
    public int getIdHash() {
        return this instanceof Item ? ((Item) this).getIdHash() : -1;
    }

    /**
     * Gets center location.
     *
     * @return the center location
     */
    public Location getCenterLocation() {
        int offset = size >> 1;
        return location.transform(offset, offset, 0);
    }

    /**
     * Gets mathematical center.
     *
     * @return the mathematical center
     */
    public Vector getMathematicalCenter() {
        Location topRight = location.transform(size - 1, size - 1, 0);
        double x = ((double) location.getX() + (double) topRight.getX()) / 2.0;
        double y = ((double) location.getY() + (double) topRight.getY()) / 2.0;
        return new Vector(x, y);
    }

    /**
     * Gets face location.
     *
     * @param fromLoc the from loc
     * @return the face location
     */
    public Location getFaceLocation(Location fromLoc) {
        Vector center = getMathematicalCenter();
        Vector fromVec = new Vector((double) fromLoc.getX(), (double) fromLoc.getY());
        Vector difference = fromVec.minus(center);
        Vector end = center.plus(difference.invert());
        return Location.create((int) end.getX(), (int) end.getY(), fromLoc.getZ());
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return StringUtils.formatDisplayName(name);
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets index.
     *
     * @param index the index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(Direction direction) {
        if (direction == null)
            return;
        this.direction = direction;
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets interaction.
     *
     * @return the interaction
     */
    public InteractPlugin getInteraction() {
        if (interactPlugin != null && !interactPlugin.isInitialized()) {
            interactPlugin.setDefault();
        }
        return interactPlugin;
    }

    /**
     * Sets interaction.
     *
     * @param interactPlugin the interact plugin
     */
    public void setInteraction(InteractPlugin interactPlugin) {
        this.interactPlugin = interactPlugin;
    }

    /**
     * Gets destination flag.
     *
     * @return the destination flag
     */
    public DestinationFlag getDestinationFlag() {
        return destinationFlag;
    }

    /**
     * Sets destination flag.
     *
     * @param destinationFlag the destination flag
     */
    public void setDestinationFlag(DestinationFlag destinationFlag) {
        this.destinationFlag = destinationFlag;
    }

    /**
     * Is renderable boolean.
     *
     * @return the boolean
     */
    public boolean isRenderable() {
        return renderable;
    }

    /**
     * Sets renderable.
     *
     * @param renderable the renderable
     */
    public void setRenderable(boolean renderable) {
        this.renderable = renderable;
    }
}
