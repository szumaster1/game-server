package content.global.handlers.item.plugins;

import core.cache.def.impl.ItemDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.Plugin;

/**
 * The Consumable option plugin.
 */
public final class ConsumableOptionPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.setOptionHandler("eat", this);
        ItemDefinition.setOptionHandler("drink", this);
        return this;
    }


    /**
     * The Last eaten.
     */
    int lastEaten = -1;

    @Override
    public boolean handle(final Player player, final Node node, final String option) {

        return true;
    }

}
