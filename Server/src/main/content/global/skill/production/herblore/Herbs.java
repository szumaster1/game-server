package content.global.skill.production.herblore;

import core.game.node.item.Item;

/**
 * The enum Herbs.
 */
public enum Herbs {
    /**
     * The Guam.
     */
    GUAM(new Item(199), 2.5, 3, new Item(249)),
    /**
     * The Marrentill.
     */
    MARRENTILL(new Item(201), 3.8, 5, new Item(251)),
    /**
     * The Tarromin.
     */
    TARROMIN(new Item(203), 5, 11, new Item(253)),
    /**
     * The Harralander.
     */
    HARRALANDER(new Item(205), 6.3, 20, new Item(255)),
    /**
     * The Ranarr.
     */
    RANARR(new Item(207), 7.5, 25, new Item(257)),
    /**
     * The Toadflax.
     */
    TOADFLAX(new Item(3049), 8, 30, new Item(2998)),
    /**
     * The Spirit weed.
     */
    SPIRIT_WEED(new Item(12174), 7.8, 35, new Item(12172)),
    /**
     * The Irit.
     */
    IRIT(new Item(209), 8.8, 40, new Item(259)),
    /**
     * The Avantoe.
     */
    AVANTOE(new Item(211), 10, 48, new Item(261)),
    /**
     * The Kwuarm.
     */
    KWUARM(new Item(213), 11.3, 54, new Item(263)),
    /**
     * The Snapdragon.
     */
    SNAPDRAGON(new Item(3051), 11.8, 59, new Item(3000)),
    /**
     * The Cadantine.
     */
    CADANTINE(new Item(215), 12.5, 65, new Item(265)),
    /**
     * The Lantadyme.
     */
    LANTADYME(new Item(2485), 13.1, 67, new Item(2481)),
    /**
     * The Dwarf weed.
     */
    DWARF_WEED(new Item(217), 13.8, 70, new Item(267)),
    /**
     * The Torstol.
     */
    TORSTOL(new Item(219), 15, 75, new Item(269)),
    /**
     * The Snake weed.
     */
    SNAKE_WEED(new Item(1525), 2.5, 3, new Item(1526)),
    /**
     * The Ardrigal.
     */
    ARDRIGAL(new Item(1527), 2.5, 3, new Item(1528)),
    /**
     * The Sito foil.
     */
    SITO_FOIL(new Item(1529), 2.5, 3, new Item(1530)),
    /**
     * The Volencia moss.
     */
    VOLENCIA_MOSS(new Item(1531), 2.5, 3, new Item(1532)),
    /**
     * The Rogues puse.
     */
    ROGUES_PUSE(new Item(1533), 2.5, 3, new Item(1534));

    private final Item herb;

    private final int level;

    private final double experience;

    private final Item product;


    Herbs(final Item herb, final double experience, final int level, final Item product) {
        this.herb = herb;
        this.experience = experience;
        this.level = level;
        this.product = product;
    }

    /**
     * Gets herb.
     *
     * @return the herb
     */
    public Item getHerb() {
        return herb;
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
     * Gets product.
     *
     * @return the product
     */
    public Item getProduct() {
        return product;
    }

    /**
     * For item herbs.
     *
     * @param item the item
     * @return the herbs
     */
    public static Herbs forItem(final Item item) {
        for (Herbs herb : Herbs.values()) {
            if (herb.getHerb().getId() == item.getId()) {
                return herb;
            }
        }
        return null;
    }
}
