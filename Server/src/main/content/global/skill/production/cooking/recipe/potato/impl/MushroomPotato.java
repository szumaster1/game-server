package content.global.skill.production.cooking.recipe.potato.impl;

import content.global.skill.production.cooking.recipe.potato.PotatoRecipe;
import core.game.node.item.Item;

/**
 * The Mushroom potato.
 */
public class MushroomPotato extends PotatoRecipe {

    private static final Item MUSHROOM_POTATO = new Item(7058);

    private static final Item TOPPING = new Item(7066);

    @Override
    public Item getProduct() {
        return MUSHROOM_POTATO;
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
        return 64;
    }

    @Override
    public double getExperience() {
        return 10;
    }

}
