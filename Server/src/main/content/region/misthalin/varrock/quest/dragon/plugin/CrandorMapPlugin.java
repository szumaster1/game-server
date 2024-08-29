package content.region.misthalin.varrock.quest.dragon.plugin;

import content.region.misthalin.varrock.quest.dragon.DragonSlayer;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.plugin.Plugin;

/**
 * Represents the Crandor map plugin.
 */
public final class CrandorMapPlugin extends UseWithHandler {

    /**
     * Instantiates a new Crandor map plugin.
     */
    public CrandorMapPlugin() {
        super(DragonSlayer.MAGIC_PIECE.getId(), DragonSlayer.MAZE_PIECE.getId(), DragonSlayer.WORMBRAIN_PIECE.getId());
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        addHandler(DragonSlayer.MAGIC_PIECE.getId(), ITEM_TYPE, this);
        addHandler(DragonSlayer.MAZE_PIECE.getId(), ITEM_TYPE, this);
        addHandler(DragonSlayer.WORMBRAIN_PIECE.getId(), ITEM_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        if (!event.getPlayer().getInventory().containsItem(DragonSlayer.MAGIC_PIECE) || !event.getPlayer().getInventory().containsItem(DragonSlayer.MAZE_PIECE) || !event.getPlayer().getInventory().containsItem(DragonSlayer.WORMBRAIN_PIECE)) {
            event.getPlayer().getPacketDispatch().sendMessage("You don't have all the map pieces yet.");
            return true;
        }
        if (event.getPlayer().getInventory().remove(DragonSlayer.MAGIC_PIECE) && event.getPlayer().getInventory().remove(DragonSlayer.MAZE_PIECE) && event.getPlayer().getInventory().remove(DragonSlayer.WORMBRAIN_PIECE)) {
            event.getPlayer().getInventory().add(DragonSlayer.CRANDOR_MAP);
            event.getPlayer().getDialogueInterpreter().sendItemMessage(DragonSlayer.CRANDOR_MAP.getId(), "You put the three pieces together and assemble a map that shows the route through the reefs to Crandor.");
        }
        return true;
    }

}
