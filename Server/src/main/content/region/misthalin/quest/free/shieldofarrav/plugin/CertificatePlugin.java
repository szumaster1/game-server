package content.region.misthalin.quest.free.shieldofarrav.plugin;

import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Certificate plugin.
 */
@Initializable
public final class CertificatePlugin extends UseWithHandler {

    private static final Item CERTIFICATE = new Item(769);

    /**
     * Instantiates a new Certificate plugin.
     */
    public CertificatePlugin() {
        super(11173);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        addHandler(11174, ITEM_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        if (player.getInventory().remove(new Item(event.getUsedItem().getId(), 1), new Item(event.getBaseItem().getId(), 1))) {
            player.getInventory().add(CERTIFICATE);
        }
        return true;
    }

}
