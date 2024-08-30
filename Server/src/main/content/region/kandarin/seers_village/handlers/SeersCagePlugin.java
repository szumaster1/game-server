package content.region.kandarin.seers_village.handlers;

import cfg.consts.Scenery;
import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * Represents the plugin used to unlock the sheers cage.
 */
@Initializable
public final class SeersCagePlugin extends OptionHandler {

    private static final String UNLOCK_OPTION = "option:unlock";
    private static final String UNLOCK_MESSAGE = "You can't unlock the pillory, you'll let all the prisoners out!";

    @Override
    public boolean handle(Player player, Node node, String option) {
        if (UNLOCK_OPTION.equals(option)) {
            player.getPacketDispatch().sendMessage(UNLOCK_MESSAGE);
            return true;
        }
        return false;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(Scenery.CAGE_6836).getHandlers().put(UNLOCK_OPTION, this);
        return this;
    }
}
