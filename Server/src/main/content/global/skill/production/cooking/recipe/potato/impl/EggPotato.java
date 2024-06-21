package content.global.skill.production.cooking.recipe.potato.impl;

import content.global.skill.production.cooking.recipe.potato.PotatoRecipe;
import core.game.node.item.Item;

/**
 * The Egg potato.
 */
public class EggPotato extends PotatoRecipe {

    private static final Item EGG_POTATO = new Item(7056);

    private static final Item TOPPING = new Item(7064);

    @Override
    public Item getProduct() {
        return EGG_POTATO;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{TOPPING};
    }

    @Override
    public boolean isTopping() {
        return true;
    }

    @Override
    public int getLevel() {
        return 51;
    }

    @Override
    public double getExperience() {
        return 10;
    }

}
