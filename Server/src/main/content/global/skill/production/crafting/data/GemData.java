package content.global.skill.production.crafting.data;

import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;

public enum GemData {
    SAPPHIRE(new Item(1623), new Item(1607), 20, new Animation(888), 50),
    EMERALD(new Item(1621), new Item(1605), 27, new Animation(889), 67),
    RUBY(new Item(1619), new Item(1603), 34, new Animation(887), 85),
    DIAMOND(new Item(1617), new Item(1601), 43, new Animation(886), 107.5),
    DRAGONSTONE(new Item(1631), new Item(1615), 55, new Animation(885), 137.5),
    ONYX(new Item(6571), new Item(6573), 67, new Animation(2717), 168),
    OPAL(new Item(1625), new Item(1609), 1, new Animation(890), 10),
    JADE(new Item(1627), new Item(1611), 13, new Animation(891), 20),
    RED_TOPAZ(new Item(1629), new Item(1613), 16, new Animation(892), 25);

    private final Item uncut;
    private final Item gem;
    private final int level;
    private final Animation animation;
    private final double exp;

    GemData(Item uncut, Item gem, int level, Animation animation, double exp) {
        this.uncut = uncut;
        this.gem = gem;
        this.level = level;
        this.animation = animation;
        this.exp = exp;
    }

    public static GemData forId(final Item item) {
        for (GemData gem : GemData.values()) {
            if (gem.getUncut().getId() == item.getId()) {
                return gem;
            }
        }
        return null;
    }

    public Item getUncut() {
        return uncut;
    }
    public Item getGem() {
        return gem;
    }
    public int getLevel() {
        return level;
    }
    public Animation getAnimation() {
        return animation;
    }
    public double getExp() {
        return exp;
    }
}
