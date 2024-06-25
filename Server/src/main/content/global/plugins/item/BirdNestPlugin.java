package content.global.plugins.item;

import content.data.tables.BirdNestDropTable;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Plugin;

/**
 * Handles the searching of a bird nest item.
 */
public final class BirdNestPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		for (BirdNestDropTable nest : BirdNestDropTable.values()) {
			nest.getNest().getDefinition().getHandlers().put("option:search", this);
		}
		return null;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		final Item item = (Item) node;
		BirdNestDropTable.forNest(item).search(player, item);
		return true;
	}

	@Override
	public boolean isWalk() {
		return false;
	}
}
