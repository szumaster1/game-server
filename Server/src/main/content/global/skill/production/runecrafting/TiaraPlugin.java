package content.global.skill.production.runecrafting;

import core.cache.def.impl.ItemDefinition;
import core.cache.def.impl.SceneryDefinition;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.setVarbit;
import static core.api.ContentAPIKt.setVarp;

/**
 * The Tiara plugin.
 */
public final class TiaraPlugin implements Plugin<Object> {
    private static final int CONFIG = 491;
    private static final int[] IDS = new int[]{5527, 5529, 5531, 5533, 5535, 5537, 5539, 5541, 5543, 5545, 5547, 5551, 5549};

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (int i : IDS) {
            ItemDefinition.forId(i).getHandlers().put("equipment", this);
        }
        return this;
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        final Player player = (Player) args[0];
        final Item item = (Item) args[1];
        switch (identifier) {
            case "equip":
                MysteriousRuin ruin = MysteriousRuin.forTalisman(Tiara.forItem(item).getTalisman());
                setVarbit(player, SceneryDefinition.forId(ruin.getObject()[0]).getVarbitID(), 1, true);
                break;
            case "unequip":
                final Item other = args.length == 2 ? null : (Item) args[2];
                if (other != null) {
                    Tiara tiara = Tiara.forItem(other);
                    if (tiara != null) {
                        MysteriousRuin r = MysteriousRuin.forTalisman(tiara.getTalisman());
                        setVarbit(player, SceneryDefinition.forId(r.getObject()[0]).getVarbitID(), 1, true);
                        break;
                    }
                }
                setVarp(player, CONFIG, 0);
                break;
        }
        return true;
    }

}
