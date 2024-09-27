package core.game.interaction;

import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;

/**
 * Node usage event.
 */
public final class NodeUsageEvent {

    private final Player player; // The player involved in the event

    private final int componentId; // The ID of the component being interacted with

    private final Node used; // The node that is being used

    private final Node with; // The node that is being used with

    /**
     * Constructs a new Node usage event.
     *
     * @param player      the player
     * @param componentId the component id
     * @param used        the used
     * @param with        the with
     */
    public NodeUsageEvent(Player player, int componentId, Node used, Node with) {
        this.player = player; // Assigning the player to the instance variable
        this.componentId = componentId; // Assigning the component ID to the instance variable
        this.used = used; // Assigning the used node to the instance variable
        this.with = with; // Assigning the with node to the instance variable
    }

    /**
     * Gets base item.
     *
     * @return the base item
     */
    public Item getBaseItem() {
        return with instanceof Item ? (Item) with : null; // Returns the 'with' node as an Item if applicable
    }

    /**
     * Gets used item.
     *
     * @return the used item
     */
    public Item getUsedItem() {
        return used instanceof Item ? (Item) used : null; // Returns the 'used' node as an Item if applicable
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player; // Returns the player involved in the event
    }

    /**
     * Gets component id.
     *
     * @return the component id
     */
    public int getComponentId() {
        return componentId; // Returns the component ID
    }

    /**
     * Gets used.
     *
     * @return the used
     */
    public Node getUsed() {
        return used; // Returns the node that is being used
    }

    /**
     * Gets used with.
     *
     * @return the used with
     */
    public Node getUsedWith() {
        return with; // Returns the node that is being used with
    }

}
