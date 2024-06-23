package content.global.handlers.item.plugins;

import core.api.consts.Sounds;
import core.cache.def.impl.ItemDefinition;
import core.game.global.action.DigSpadeHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.playAudio;

/**
 * The Spade dig option plugin.
 */
@Initializable
public class SpadeDigOptionPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.forId(952).getHandlers().put("option:dig", this);
        return null;
    }

    @Override
    public boolean handle(final Player player, Node node, String option) {
        playAudio(player, Sounds.DIGSPADE_1470);
        if (!DigSpadeHandler.dig(player)) {
            player.sendMessage("You dig but find nothing.");
        }
        return true;
    }

    @Override
    public boolean isWalk() {
        return false;
    }

}
