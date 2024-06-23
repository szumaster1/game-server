package content.global.handlers.item.plugins;

import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Water skin plugin.
 */
@Initializable
public final class WaterSkinPlugin extends UseWithHandler {

    private final Item FULL_SKIN = new Item(1823);

    private final int[][] data = new int[][]{{1937, 1935}, {1929, 1925}, {1921, 1923}, {227, 229}};

    /**
     * Instantiates a new Water skin plugin.
     */
    public WaterSkinPlugin() {
        super(1825, 1827, 1829, 1831);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (int i = 0; i < data.length; i++) {
            addHandler(data[i][0], ITEM_TYPE, this);
        }
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Item waterSkin = event.getUsedItem().getName().contains("skin") ? event.getUsedItem() : event.getBaseItem();
        final Item vessil = !event.getUsedItem().getName().contains("skin") ? event.getUsedItem() : event.getBaseItem();
        if (event.getPlayer().getInventory().remove(waterSkin)) {
            event.getPlayer().getInventory().add(vessil.getId() == 227 ? new Item(waterSkin.getId() - 2) : FULL_SKIN);
            for (int i = 0; i < data.length; i++) {
                if (data[i][0] == vessil.getId() && event.getPlayer().getInventory().remove(vessil)) {
                    event.getPlayer().getInventory().add(new Item(data[i][1]));
                }
            }
        }
        return true;
    }

}
