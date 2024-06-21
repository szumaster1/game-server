package content.global.interaction.item.plugins;

import core.cache.def.impl.ItemDefinition;
import core.game.global.action.PickupHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItem;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.removeAttribute;

/**
 * The Pickup plugin.
 */
@Initializable
public final class PickupPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.setOptionHandler("take", this);
        return this;
    }

    @Override
    public boolean handle(final Player player, Node node, String option) {
        if (player.getAttributes().containsKey("pickup")) return false;
        //setAttribute(player, "pickup", "true");
        boolean handleResult = PickupHandler.take(player, (GroundItem) node);
        removeAttribute(player, "pickup");
        return handleResult;
    }

    @Override
    public Location getDestination(Node node, Node item) {
        return null;
    }

}
