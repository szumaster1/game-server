package content.global.skill.production.cooking.recipe.topping.impl;

import content.global.skill.production.cooking.recipe.topping.ToppingRecipe;
import core.game.node.item.Item;

/**
 * The Uncooked egg.
 */
public final class UncookedEgg extends ToppingRecipe {

    private static final Item EGG = new Item(1944);

    private static final Item UNCOOKED_EGG = new Item(7076);

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public double getExperience() {
        return 1;
    }

    @Override
    public Item getProduct() {
        return UNCOOKED_EGG;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{EGG};
    }

    @Override
    public boolean isSingular() {
        return true;
    }

    @Override
    public Item[] getParts() {
        return new Item[]{};
    }

}
