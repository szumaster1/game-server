package core.game.node.item;

import core.cache.def.impl.ItemDefinition;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.plugin.Plugin;

public abstract class ItemPlugin implements Plugin<Object> {

    protected static final int DROP = 1;

    public ItemPlugin() {
        /**
         * empty.
         */
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return this;
    }

    public void register(int... ids) {
        for (int id : ids) {
            ItemDefinition.forId(id).setItemPlugin(this);
        }
    }

    public void remove(Player player, Item item, int type) {

    }

    public boolean canPickUp(Player player, GroundItem item, int type) {
        return true;
    }

    public boolean createDrop(Item item, Player player, NPC npc, Location l) {
        return true;
    }

    public Item getItem(Item item, NPC npc) {
        return item;
    }

    public Item getDeathItem(Item item) {
        return item;
    }
}
