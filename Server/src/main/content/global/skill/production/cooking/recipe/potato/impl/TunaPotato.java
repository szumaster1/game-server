package content.global.skill.production.cooking.recipe.potato.impl;

import content.global.skill.production.cooking.recipe.potato.PotatoRecipe;
import core.game.node.item.Item;

/**
 * The Tuna potato.
 */
public class TunaPotato extends PotatoRecipe {
    private static final Item TUNA_POTATO = new Item(7060);
    private static final Item TOPPING = new Item(7068);

    @Override
    public Item getProduct() {
        return TUNA_POTATO;
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
        return 68;
    }

    @Override
    public double getExperience() {
        return 10;
    }

}
