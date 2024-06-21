package content.global.skill.production.cooking.recipe.potato.impl;

import content.global.skill.production.cooking.recipe.potato.PotatoRecipe;
import core.game.node.item.Item;

/**
 * The Cheese potato.
 */
public class CheesePotato extends PotatoRecipe {

    private static final Item CHEESE_POTATO = new Item(6705);

    private static final Item CHEESE = new Item(1985);

    @Override
    public Item getProduct() {
        return CHEESE_POTATO;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{CHEESE};
    }

    @Override
    public boolean isTopping() {
        return false;
    }

    @Override
    public int getLevel() {
        return 47;
    }

    @Override
    public double getExperience() {
        return 10;
    }

}
