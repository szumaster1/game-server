package core.game.node.item;

import core.cache.def.impl.ItemDefinition;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.plugin.Plugin;

/**
 * Item plugin.
 */
public abstract class ItemPlugin implements Plugin<Object> {

    /**
     * The constant DROP.
     */
    protected static final int DROP = 1;

    /**
     * Instantiates a new Item plugin.
     */
    public ItemPlugin() {
        /*
         * empty.
         */
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return this;
    }

    /**
     * Register.
     *
     * @param ids the ids
     */
    public void register(int... ids) {
        for (int id : ids) {
            ItemDefinition.forId(id).setItemPlugin(this);
        }
    }

    /**
     * Remove.
     *
     * @param player the player
     * @param item   the item
     * @param type   the type
     */
    public void remove(Player player, Item item, int type) {

    }

    /**
     * Can pick up boolean.
     *
     * @param player the player
     * @param item   the item
     * @param type   the type
     * @return the boolean
     */
    public boolean canPickUp(Player player, GroundItem item, int type) {
        return true;
    }

    /**
     * Create drop boolean.
     *
     * @param item   the item
     * @param player the player
     * @param npc    the npc
     * @param l      the l
     * @return the boolean
     */
    public boolean createDrop(Item item, Player player, NPC npc, Location l) {
        return true;
    }

    /**
     * Gets item.
     *
     * @param item the item
     * @param npc  the npc
     * @return the item
     */
    public Item getItem(Item item, NPC npc) {
        return item;
    }

    /**
     * Gets death item.
     *
     * @param item the item
     * @return the death item
     */
    public Item getDeathItem(Item item) {
        return item;
    }
}
