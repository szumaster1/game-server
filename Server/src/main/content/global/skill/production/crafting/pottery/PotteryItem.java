package content.global.skill.production.crafting.pottery;

import core.game.node.item.Item;

/**
 * The enum Pottery item.
 */
public enum PotteryItem {
    /**
     * The Pot.
     */
    POT(new Item(1787), new Item(1931), 1, 6.3, 6.3),
    /**
     * The Dish.
     */
    DISH(new Item(1789), new Item(2313), 7, 15, 10),
    /**
     * The Bowl.
     */
    BOWL(new Item(1791), new Item(1923), 8, 18, 15),
    /**
     * The Plant.
     */
    PLANT(new Item(5352), new Item(5350), 19, 20, 17.5),
    /**
     * The Lid.
     */
    LID(new Item(4438), new Item(4440), 25, 20, 20);

    private final Item unfinished;
    private final Item product;
    private final int level;
    private final double exp;
    private final double fireExp;


    PotteryItem(Item unfinished, Item product, int level, double exp, double fireExp) {
        this.unfinished = unfinished;
        this.product = product;
        this.level = level;
        this.exp = exp;
        this.fireExp = fireExp;
    }

    /**
     * For id pottery item.
     *
     * @param id the id
     * @return the pottery item
     */
    public static PotteryItem forId(int id) {
        for (PotteryItem def : PotteryItem.values()) {
            if (def.getUnfinished().getId() == id) {
                return def;
            }
        }
        return null;
    }

    /**
     * Gets unfinished.
     *
     * @return the unfinished
     */
    public Item getUnfinished() {
        return unfinished;
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
     * Gets exp.
     *
     * @return the exp
     */
    public double getExp() {
        return exp;
    }

    /**
     * Gets fire exp.
     *
     * @return the fire exp
     */
    public double getFireExp() {
        return fireExp;
    }

}
