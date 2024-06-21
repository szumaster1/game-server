package content.global.skill.production.herblore.potions;

import core.game.node.item.Item;

/**
 * The Generic potion.
 */
public final class GenericPotion {

    private final Item base;

    private final Item ingredient;

    private final int level;

    private final double experience;

    private final Item product;

    /**
     * Instantiates a new Generic potion.
     *
     * @param base       the base
     * @param ingredient the ingredient
     * @param level      the level
     * @param experience the experience
     * @param product    the product
     */
    public GenericPotion(final Item base, final Item ingredient, final int level, final double experience, final Item product) {
        this.base = base;
        this.ingredient = ingredient;
        this.level = level;
        this.experience = experience;
        this.product = product;
    }

    /**
     * Transform generic potion.
     *
     * @param potion the potion
     * @return the generic potion
     */
    public static GenericPotion transform(final UnfinishedPotion potion) {
        return new GenericPotion(potion.getBase(), potion.getIngredient(), potion.getLevel(), 0, potion.getPotion());
    }

    /**
     * Transform generic potion.
     *
     * @param potion the potion
     * @return the generic potion
     */
    public static GenericPotion transform(final FinishedPotion potion) {
        return new GenericPotion(potion.getUnfinished().getPotion(), potion.getIngredient(), potion.getLevel(), potion.getExperience(), potion.getPotion());
    }

    /**
     * Gets base.
     *
     * @return the base
     */
    public Item getBase() {
        return base;
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
     * Gets ingredient.
     *
     * @return the ingredient
     */
    public Item getIngredient() {
        return ingredient;
    }
}
