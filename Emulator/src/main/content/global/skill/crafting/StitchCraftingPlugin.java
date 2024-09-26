package content.global.skill.crafting;

import core.game.component.Component;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;

@Initializable
public class StitchCraftingPlugin extends UseWithHandler {

	public StitchCraftingPlugin() {
		super(1741);
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		Player player = event.getPlayer();
		int itemId = event.getUsedItem().getId();
		player.getAttributes().put("leatherId", itemId);
		player.getInterfaceManager().open(new Component(154));
		return true;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(1733, ITEM_TYPE, this);
		return null;
	}

}
