package content.global.skill.production.crafting.data;

import core.game.node.item.Item;

public enum PotteryData {
    POT(new Item(1787), new Item(1931), 1, 6.3, 6.3),
    DISH(new Item(1789), new Item(2313), 7, 15, 10),
    BOWL(new Item(1791), new Item(1923), 8, 18, 15),
    PLANT(new Item(5352), new Item(5350), 19, 20, 17.5),
    LID(new Item(4438), new Item(4440), 25, 20, 20);

    private final Item unfinished;
    private final Item product;
    private final int level;
    private final double exp;
    private final double fireExp;

    PotteryData(Item unfinished, Item product, int level, double exp, double fireExp) {
        this.unfinished = unfinished;
        this.product = product;
        this.level = level;
        this.exp = exp;
        this.fireExp = fireExp;
    }

    public static PotteryData forId(int id) {
        for (PotteryData def : PotteryData.values()) {
            if (def.getUnfinished().getId() == id) {
                return def;
            }
        }
        return null;
    }

    public Item getUnfinished() {
        return unfinished;
    }
    public Item getProduct() {
        return product;
    }
    public int getLevel() {
        return level;
    }
    public double getExp() {
        return exp;
    }
    public double getFireExp() {
        return fireExp;
    }
}
