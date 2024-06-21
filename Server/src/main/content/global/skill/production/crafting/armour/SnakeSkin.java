package content.global.skill.production.crafting.armour;

import core.game.node.item.Item;

/**
 * The enum Snake skin.
 */
public enum SnakeSkin {
    /**
     * The Snakeskin boot.
     */
    SNAKESKIN_BOOT(new Item(6328), 45, 30, 6),
    /**
     * The Snakeskin vambraces.
     */
    SNAKESKIN_VAMBRACES(new Item(6330), 47, 35, 8),
    /**
     * The Snakeskin bandana.
     */
    SNAKESKIN_BANDANA(new Item(6326), 48, 45, 5),
    /**
     * The Snakeskin chaps.
     */
    SNAKESKIN_CHAPS(new Item(6324), 51, 50, 12),
    /**
     * The Snakeskin body.
     */
    SNAKESKIN_BODY(new Item(6322), 53, 55, 15);

    private final Item product;
    private final int level;
    private final double experience;
    private final int requiredAmount;

    SnakeSkin(Item product, int level, double experience, int requiredAmount) {
        this.product = product;
        this.level = level;
        this.experience = experience;
        this.requiredAmount = requiredAmount;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Item getProduct() {
        return product;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets experience.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
    }

    /**
     * Gets required amount.
     *
     * @return the required amount
     */
    public int getRequiredAmount() {
        return requiredAmount;
    }

}
