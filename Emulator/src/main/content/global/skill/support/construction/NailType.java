package content.global.skill.support.construction;


import cfg.consts.Items;
import core.game.node.entity.player.Player;
import core.tools.RandomFunction;

/**
 * The enum Nail type.
 */
public enum NailType {

    /**
     * Bronze nail type.
     */
    BRONZE(Items.BRONZE_NAILS_4819, 5),
    /**
     * Iron nail type.
     */
    IRON(Items.IRON_NAILS_4820, 7),
    /**
     * Black nail type.
     */
    BLACK(Items.BLACK_NAILS_4821, 8),
    /**
     * Steel nail type.
     */
    STEEL(Items.STEEL_NAILS_1539, 10),
    /**
     * Mithril nail type.
     */
    MITHRIL(Items.MITHRIL_NAILS_4822, 13),
    /**
     * Adamant nail type.
     */
    ADAMANT(Items.ADAMANTITE_NAILS_4823, 15),
    /**
     * Rune nail type.
     */
    RUNE(Items.RUNE_NAILS_4824, 20);

    private final int itemId;

    private final int bendRate;

    private NailType(int itemId, int bendRate) {
        this.itemId = itemId;
        this.bendRate = bendRate;
    }

    /**
     * Is bend boolean.
     *
     * @return the boolean
     */
    public boolean isBend() {
        return RandomFunction.getRandom(bendRate) == 0;
    }

    /**
     * Get nail type.
     *
     * @param player the player
     * @param amount the amount
     * @return the nail type
     */
    public static NailType get(Player player, int amount) {
        for (int i = values().length - 1; i >= 0; i--) {
            NailType type = values()[i];
            if (player.getInventory().contains(type.itemId, amount)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Gets bend rate.
     *
     * @return the bend rate
     */
    public int getBendRate() {
        return bendRate;
    }
}
