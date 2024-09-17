package content.region.kandarin.seers_village.handlers;

import org.rs.consts.Scenery;
import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.game.shops.Shops;

/**
 * Represents the buy crate option plugin for the Seers Village.
 */
@Initializable
public final class CratePlugin extends OptionHandler {

    private static final int SHOP_ID = 93;

    @Override
    public boolean handle(Player player, Node node, String option) {
        if ("buy".equals(option)) {
            Shops.openId(player, SHOP_ID);
            return true;
        }
        return false;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(Scenery.CRATE_6839).getHandlers().put("option:buy", this);
        return this;
    }
}
