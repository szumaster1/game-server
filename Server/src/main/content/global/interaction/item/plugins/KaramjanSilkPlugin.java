package content.global.interaction.item.plugins;

import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Karamjan silk plugin.
 */
@Initializable
public final class KaramjanSilkPlugin extends UseWithHandler {

    private static final Item CLOTH = new Item(3188);

    private static final Item SILK = new Item(950);

    /**
     * Instantiates a new Karamjan silk plugin.
     */
    public KaramjanSilkPlugin() {
        super(950);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        addHandler(431, ITEM_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        if (player.getInventory().remove(SILK)) {
            player.getInventory().add(CLOTH);
            player.getPacketDispatch().sendMessage("You pour some of the Karamjan rum over the silk.");
        }
        return true;
    }

}