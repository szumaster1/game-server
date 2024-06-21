package content.global.skill.production.herblore;

import core.game.node.item.Item;

/**
 * The enum Tars.
 */
public enum Tars {
    /**
     * The Guam tar.
     */
    GUAM_TAR(Herbs.GUAM.getProduct(), 19, 30, new Item(10142)),
    /**
     * The Marrentill tar.
     */
    MARRENTILL_TAR(Herbs.MARRENTILL.getProduct(), 31, 42.5, new Item(10143)),
    /**
     * The Tarromin tar.
     */
    TARROMIN_TAR(Herbs.TARROMIN.getProduct(), 39, 55, new Item(10144)),
    /**
     * The Harralander tar.
     */
    HARRALANDER_TAR(Herbs.HARRALANDER.getProduct(), 44, 72.5, new Item(10145));

    private final Item ingredient;

    private final int level;

    private final double experience;

    private final Item tar;


    Tars(Item ingredient, int level, double experience, Item tar) {
        this.ingredient = ingredient;
        this.level = level;
        this.experience = experience;
        this.tar = tar;
    }

    /**
     * Gets ingredient.
     *
     * @return the ingredient
     */
    public Item getIngredient() {
        return ingredient;
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
     * Gets tar.
     *
     * @return the tar
     */
    public Item getTar() {
        return tar;
    }

    /**
     * For item tars.
     *
     * @param item the item
     * @return the tars
     */
    public static Tars forItem(final Item item) {
        for (Tars tar : Tars.values()) {
            if (tar.getIngredient().getId() == item.getId()) {
                return tar;
            }
        }
        return null;
    }
}
