package content.global.skill.production.cooking.recipe.pie.impl;

import content.global.skill.production.cooking.recipe.pie.PieRecipe;
import core.game.node.item.Item;

/**
 * The Redberry pie.
 */
public class RedberryPie extends PieRecipe {

    private static final Item UNCOOKED_PIE = new Item(2321);

    private static final Item REDBERRIES = new Item(1951);

    @Override
    public Item getProduct() {
        return UNCOOKED_PIE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{REDBERRIES};
    }

}
