package content.global.travel.ship;

import core.game.component.Component;
import core.game.component.ComponentDefinition;
import core.game.component.ComponentPlugin;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Ship charter interface.
 */
@Initializable
public class ShipCharterInterface extends ComponentPlugin {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ComponentDefinition.put(95, this);
        return this;
    }

    @Override
    public boolean handle(Player player, Component component, int opcode, int button, int slot, int itemId) {
        ShipCharter.handle(player, button);
        return true;
    }

}
