package content.global.skill.production.cooking;

import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Fire option pottery plugin.
 */
@Initializable
public final class FireOptionPotteryPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(2643).getHandlers().put("option:fire", this);
        SceneryDefinition.forId(4308).getHandlers().put("option:fire", this);
        SceneryDefinition.forId(11601).getHandlers().put("option:fire", this);
        SceneryDefinition.forId(34802).getHandlers().put("option:fire", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        player.faceLocation(node.getLocation());
        player.getDialogueInterpreter().open(99843, true, true);
        return true;
    }

}
