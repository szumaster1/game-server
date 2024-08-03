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

public final class GroundItemManager {

    private static final List<GroundItem> GROUND_ITEMS = new ArrayList<>(20);

    public static GroundItem create(Item item, Location location) {
        return create(new GroundItem(item, location, null));
    }

    public static GroundItem create(Item item, Location location, int playerUid, int ticks) {
        return create(new GroundItem(item, location, playerUid, ticks));
    }

    public static GroundItem create(Item item, final Player player) {
        return create(new GroundItem(item, player.getLocation(), player));
    }

    public static GroundItem create(Item item, Location location, Player player) {
        return create(new GroundItem(item, location, player));
    }

    public static void create(Item[] item, Location location, Player player) {
        for (int i = 0; i < item.length; i++) {
            create(new GroundItem(item[i], location, player));
        }
    }

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

    public static GroundItem get(int itemId, Location location, Player player) {
        return RegionManager.getRegionPlane(location).getItem(itemId, location, player);
    }

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

    public static List<GroundItem> getItems() {
        return GROUND_ITEMS;
    }
}
