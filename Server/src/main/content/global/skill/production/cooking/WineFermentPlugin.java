package content.global.skill.production.cooking;

import content.global.skill.production.cooking.fermenting.WineFermentingPulse;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Wine ferment plugin.
 */
@Initializable
public final class WineFermentPlugin extends UseWithHandler {

    private static final Item GRAPES = new Item(1987, 1);

    private static final Item JUG_OF_WATER = new Item(1937, 1);

    private static final Item UNFERMENTED_WINE = new Item(1995, 1);

    /**
     * Instantiates a new Wine ferment plugin.
     */
    public WineFermentPlugin() {
        super(1937);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        addHandler(1987, ITEM_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        if (player.getSkills().getLevel(Skills.COOKING) < 35) {
            player.getPacketDispatch().sendMessage("You need a cooking level of 35 to do this.");
            return true;
        }
        if (player.getInventory().remove(GRAPES, JUG_OF_WATER)) {
            player.getInventory().add(UNFERMENTED_WINE);
            GameWorld.getPulser().submit(new WineFermentingPulse(1, player));
        }
        return true;
    }

}
