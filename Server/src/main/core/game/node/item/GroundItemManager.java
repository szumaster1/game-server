package core.game.node.item;

import core.game.bots.AIRepository;
import core.game.node.entity.player.Player;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.chunk.ItemUpdateFlag;
import core.network.packet.PacketRepository;
import core.network.packet.context.BuildItemContext;
import core.network.packet.outgoing.UpdateGroundItemAmount;

import java.util.ArrayList;
import java.util.List;

/**
 * Ground item manager.
 */
public final class GroundItemManager {

    private static final List<GroundItem> GROUND_ITEMS = new ArrayList<>(20);

    /**
     * Create ground item.
     *
     * @param item     the item
     * @param location the location
     * @return the ground item
     */
    public static GroundItem create(Item item, Location location) {
        return create(new GroundItem(item, location, null));
    }

    /**
     * Create ground item.
     *
     * @param item      the item
     * @param location  the location
     * @param playerUid the player uid
     * @param ticks     the ticks
     * @return the ground item
     */
    public static GroundItem create(Item item, Location location, int playerUid, int ticks) {
        return create(new GroundItem(item, location, playerUid, ticks));
    }

    /**
     * Create ground item.
     *
     * @param item   the item
     * @param player the player
     * @return the ground item
     */
    public static GroundItem create(Item item, final Player player) {
        return create(new GroundItem(item, player.getLocation(), player));
    }

    /**
     * Create ground item.
     *
     * @param item     the item
     * @param location the location
     * @param player   the player
     * @return the ground item
     */
    public static GroundItem create(Item item, Location location, Player player) {
        return create(new GroundItem(item, location, player));
    }

    /**
     * Create.
     *
     * @param item     the item
     * @param location the location
     * @param player   the player
     */
    public static void create(Item[] item, Location location, Player player) {
        for (int i = 0; i < item.length; i++) {
            create(new GroundItem(item[i], location, player));
        }
    }

    /**
     * Create ground item.
     *
     * @param item the item
     * @return the ground item
     */
    public static GroundItem create(GroundItem item) {
        if (!item.getDefinition().isTradeable()) {
            item.setRemainPrivate(true);
        }
        if (item.getDropper() != null && item.hasItemPlugin()) {
            item.getPlugin().remove(item.getDropper(), item, ItemPlugin.DROP);
        }
        item.setRemoved(false);
        RegionManager.getRegionPlane(item.getLocation()).add(item);
        if (GROUND_ITEMS.add(item)) {
            return item;
        }
        return null;
    }

    /**
     * Destroy ground item.
     *
     * @param item the item
     * @return the ground item
     */
    public static GroundItem destroy(GroundItem item) {
        if (item == null) {
            return null;
        }
        GROUND_ITEMS.remove(item);
        RegionManager.getRegionPlane(item.getLocation()).remove(item);
        if (item.isAutoSpawn()) {
            item.respawn();
        }
        return item;
    }

    /**
     * Get ground item.
     *
     * @param itemId   the item id
     * @param location the location
     * @param player   the player
     * @return the ground item
     */
    public static GroundItem get(int itemId, Location location, Player player) {
        return RegionManager.getRegionPlane(location).getItem(itemId, location, player);
    }

    /**
     * Increase ground item.
     *
     * @param item     the item
     * @param location the location
     * @param p        the p
     * @return the ground item
     */
    public static GroundItem increase(Item item, Location location, Player p) {
        GroundItem g = get(item.getId(), location, p);
        if (g == null || !g.droppedBy(p) || !g.isPrivate() || g.isRemoved()) {
            return create(item, location, p);
        }
        int oldAmount = g.getAmount();
        g.setAmount(oldAmount + item.getAmount());
        PacketRepository.send(UpdateGroundItemAmount.class, new BuildItemContext(p, g, oldAmount));
        return g;
    }

    /**
     * Pulse.
     */
    public static void pulse() {
        Object[] giArray = GROUND_ITEMS.toArray();
        int size = giArray.length;
        for (int i = 0; i < size; i++) {
            GroundItem item = (GroundItem) giArray[i];
            if (item.isAutoSpawn()) {
                continue;
            }
            if (!item.isActive()) {
                GROUND_ITEMS.remove(item);
                if (item.getDropper() != null) {
                    if (item.getDropper().isArtificial()) {
                        ArrayList<GroundItem> val = AIRepository.getItems(item.getDropper());
                        if (val != null)
                            val.remove(item);
                    }
                }
                if (!item.isRemoved()) {
                    RegionManager.getRegionPlane(item.getLocation()).remove(item);
                }
            } else if (!item.isRemainPrivate() && item.getDecayTime() - GameWorld.getTicks() == 100) {
                RegionManager.getRegionChunk(item.getLocation()).flag(new ItemUpdateFlag(item, ItemUpdateFlag.CONSTRUCT_TYPE));
            }
        }
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public static List<GroundItem> getItems() {
        return GROUND_ITEMS;
    }
}
