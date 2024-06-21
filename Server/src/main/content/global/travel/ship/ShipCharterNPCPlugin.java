package content.global.travel.ship;

import core.cache.def.impl.NPCDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Ship charter npc plugin.
 */
@Initializable
public final class ShipCharterNPCPlugin extends OptionHandler {

    private static final int[] IDS = new int[]{4650, 4651, 4652, 4653, 4654, 4655, 4656};

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (int id : IDS) {
            NPCDefinition.forId(id).getHandlers().put("option:charter", this);
        }
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        ShipCharter.open(player);
        return true;
    }

}
