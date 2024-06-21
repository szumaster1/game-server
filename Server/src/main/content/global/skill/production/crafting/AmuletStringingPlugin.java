package content.global.skill.production.crafting;

import content.global.skill.production.crafting.jewellery.JewelleryCrafting;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Amulet stringing plugin.
 */
@Initializable
public final class AmuletStringingPlugin extends UseWithHandler {

    /**
     * Instantiates a new Amulet stringing plugin.
     */
    public AmuletStringingPlugin() {
        super(1673, 1675, 1677, 1679, 1681, 1683, 6579);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        addHandler(1759, ITEM_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        final JewelleryCrafting.JewelleryItem data = JewelleryCrafting.JewelleryItem.forProduct(event.getUsedItem().getId() == 6579 ? 6579 : event.getUsedWith().getId());
        if (data == null) {
            return true;
        }
        if (player.getSkills().getLevel(Skills.CRAFTING) < data.getLevel()) {
            player.getPacketDispatch().sendMessage("You need a Crafting level of at least " + data.getLevel() + " to do that.");
            return true;
        }
        if (player.getInventory().remove(event.getUsedItem(), event.getBaseItem())) {
            player.getInventory().add(new Item(data == JewelleryCrafting.JewelleryItem.ONYX_AMULET ? 6581 : data.getSendItem() + 19));
        }
        return true;
    }

}
