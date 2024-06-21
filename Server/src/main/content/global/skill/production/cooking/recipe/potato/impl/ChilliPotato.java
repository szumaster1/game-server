package content.global.skill.production.cooking.recipe.potato.impl;

import content.global.skill.production.cooking.recipe.potato.PotatoRecipe;
import core.game.node.item.Item;

/**
 * The Chilli potato.
 */
public class ChilliPotato extends PotatoRecipe {

    private static final Item CHILLI_POTATO = new Item(7054);

    private static final Item CHILLI_CON_CARNE = new Item(7062);

    @Override
    public Item getProduct() {
        return CHILLI_POTATO;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{CHILLI_CON_CARNE};
    }

    @Override
    public boolean isTopping() {
        return true;
    }

    @Override
    public int getLevel() {
        return 41;
    }

    @Override
    public double getExperience() {
        return 10;
    }

}
