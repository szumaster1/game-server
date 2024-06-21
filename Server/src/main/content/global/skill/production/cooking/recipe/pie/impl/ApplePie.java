package content.global.skill.production.cooking.recipe.pie.impl;

import content.global.skill.production.cooking.recipe.pie.PieRecipe;
import core.game.node.item.Item;

/**
 * The Apple pie.
 */
public class ApplePie extends PieRecipe {

    private static final Item UNCOOKED_PIE = new Item(2317);

    private static final Item COOKING_APPLE = new Item(1955);

    @Override
    public Item getProduct() {
        return UNCOOKED_PIE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{COOKING_APPLE};
    }

}
