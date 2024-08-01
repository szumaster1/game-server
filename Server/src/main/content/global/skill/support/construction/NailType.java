package content.global.skill.support.construction;


import core.api.consts.Items;
import core.game.node.entity.player.Player;
import core.tools.RandomFunction;

public enum NailType {

    BRONZE(Items.BRONZE_NAILS_4819, 5),
    IRON(Items.IRON_NAILS_4820, 7),
    BLACK(Items.BLACK_NAILS_4821, 8),
    STEEL(Items.STEEL_NAILS_1539, 10),
    MITHRIL(Items.MITHRIL_NAILS_4822, 13),
    ADAMANT(Items.ADAMANTITE_NAILS_4823, 15),
    RUNE(Items.RUNE_NAILS_4824, 20);

    private final int itemId;

    private final int bendRate;

    private NailType(int itemId, int bendRate) {
        this.itemId = itemId;
        this.bendRate = bendRate;
    }

    public boolean isBend() {
        return RandomFunction.getRandom(bendRate) == 0;
    }

    public static NailType get(Player player, int amount) {
        for (int i = values().length - 1; i >= 0; i--) {
            NailType type = values()[i];
            if (player.getInventory().contains(type.itemId, amount)) {
                return type;
            }
        }
        return null;
    }

    public int getItemId() {
        return itemId;
    }

    public int getBendRate() {
        return bendRate;
    }
}
