package content.global.skill.production.crafting.gem;

import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;

/**
 * The enum Gems.
 */
public enum Gems {
    /**
     * The Sapphire.
     */
    SAPPHIRE(new Item(1623), new Item(1607), 20, new Animation(888), 50),
    /**
     * The Emerald.
     */
    EMERALD(new Item(1621), new Item(1605), 27, new Animation(889), 67),
    /**
     * The Ruby.
     */
    RUBY(new Item(1619), new Item(1603), 34, new Animation(887), 85),
    /**
     * The Diamond.
     */
    DIAMOND(new Item(1617), new Item(1601), 43, new Animation(886), 107.5),
    /**
     * The Dragonstone.
     */
    DRAGONSTONE(new Item(1631), new Item(1615), 55, new Animation(885), 137.5),
    /**
     * The Onyx.
     */
    ONYX(new Item(6571), new Item(6573), 67, new Animation(2717), 168),
    /**
     * The Opal.
     */
    OPAL(new Item(1625), new Item(1609), 1, new Animation(890), 10),
    /**
     * The Jade.
     */
    JADE(new Item(1627), new Item(1611), 13, new Animation(891), 20),
    /**
     * The Red topaz.
     */
    RED_TOPAZ(new Item(1629), new Item(1613), 16, new Animation(892), 25);

    private final Item uncut;

    private final Item gem;

    private final int level;

    private final Animation animation;

    private final double exp;

    Gems(Item uncut, Item gem, int level, Animation animation, double exp) {
        this.uncut = uncut;
        this.gem = gem;
        this.level = level;
        this.animation = animation;
        this.exp = exp;
    }

    /**
     * For id gems.
     *
     * @param item the item
     * @return the gems
     */
    public static Gems forId(final Item item) {
        for (Gems gem : Gems.values()) {
            if (gem.getUncut().getId() == item.getId()) {
                return gem;
            }
        }
        return null;
    }

    /**
     * Gets uncut.
     *
     * @return the uncut
     */
    public Item getUncut() {
        return uncut;
    }

    /**
     * Gets gem.
     *
     * @return the gem
     */
    public Item getGem() {
        return gem;
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
     * Gets animation.
     *
     * @return the animation
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Gets exp.
     *
     * @return the exp
     */
    public double getExp() {
        return exp;
    }
}
