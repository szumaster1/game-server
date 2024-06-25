package content.global.plugins.item;

import core.cache.def.impl.ItemDefinition;
import core.game.component.Component;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.RandomFunction;

/**
 * The Gnomecopter ticket plugin.
 */
@Initializable
public final class GnomecopterTicketPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.forId(12843).getHandlers().put("option:read", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        player.getInterfaceManager().open(new Component(729));
        String info = "Gnomecopter ticket:";
        info += "<br>" + "Castle Wars"; // Destination
        info += "<br>" + "Ref. #000";
        for (int i = 3; i < 8; i++) {
            info += RandomFunction.randomize(10);
        }
        player.getPacketDispatch().sendString(info, 729, 2);
        return true;
    }

}
