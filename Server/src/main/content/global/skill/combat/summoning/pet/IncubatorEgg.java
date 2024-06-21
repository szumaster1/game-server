package content.global.skill.combat.summoning.pet;

import core.game.node.item.Item;

/**
 * The enum Incubator egg.
 */
public enum IncubatorEgg {
    /**
     * The Penguin.
     */
    PENGUIN(new Item(12483), 30, 30, new Item(12481)),
    /**
     * The Raven.
     */
    RAVEN(new Item(11964), 50, 30, new Item(12484)),
    /**
     * The Saradomin owl.
     */
    SARADOMIN_OWL(new Item(5077), 70, 60, new Item(12503)),
    /**
     * The Zamorak hawk.
     */
    ZAMORAK_HAWK(new Item(5076), 70, 60, new Item(12506)),
    /**
     * The Guthix raptor.
     */
    GUTHIX_RAPTOR(new Item(5078), 70, 60, new Item(12509)),
    /**
     * The Vulture.
     */
    VULTURE(new Item(11965), 85, 60, new Item(12498)),
    /**
     * The Chameleon.
     */
    CHAMELEON(new Item(12494), 90, 60, new Item(12492)),
    /**
     * The Red dragon.
     */
    RED_DRAGON(new Item(12477), 99, 60, new Item(12469)),
    /**
     * The Black dragon.
     */
    BLACK_DRAGON(new Item(12480), 99, 60, new Item(12475)),
    /**
     * The Blue dragon.
     */
    BLUE_DRAGON(new Item(12478), 99, 60, new Item(12471)),
    /**
     * The Green dragon.
     */
    GREEN_DRAGON(new Item(12479), 99, 60, new Item(12473));

    private final Item egg;

    private final int level;

    private final int inucbationTime;

    private final Item product;

    IncubatorEgg(Item egg, int level, int inucbationTime, Item product) {
        this.egg = egg;
        this.level = level;
        this.inucbationTime = inucbationTime;
        this.product = product;
    }

    /**
     * For item incubator egg.
     *
     * @param item the item
     * @return the incubator egg
     */
    public static IncubatorEgg forItem(Item item) {
        for (IncubatorEgg e : values()) {
            if (e.getEgg().getId() == item.getId()) {
                return e;
            }
        }
        return null;
    }

    /**
     * Gets egg.
     *
     * @return the egg
     */
    public Item getEgg() {
        return egg;
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
     * Gets incubation time.
     *
     * @return the incubation time
     */
    public int getIncubationTime() {
        return inucbationTime;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Item getProduct() {
        return product;
    }

}