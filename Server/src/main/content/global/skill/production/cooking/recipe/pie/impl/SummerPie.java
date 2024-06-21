package content.global.skill.production.cooking.recipe.pie.impl;

import content.global.skill.production.cooking.recipe.pie.PieRecipe;
import core.game.node.item.Item;

/**
 * The Summer pie.
 */
public class SummerPie extends PieRecipe {

    private static final Item UNCOOKED_PIE = new Item(7216);

    private static final Item STRAWBERRY = new Item(5504);

    private static final Item WATERMELON = new Item(5982);

    private static final Item COOKING_APPLE = new Item(1955);

    private static final Item PART_ONE = new Item(7212);

    private static final Item PART_TWO = new Item(7214);

    @Override
    public Item getProduct() {
        return UNCOOKED_PIE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{STRAWBERRY, WATERMELON, COOKING_APPLE};
    }

    @Override
    public Item[] getParts() {
        return new Item[]{PIE_SHELL, PART_ONE, PART_TWO, UNCOOKED_PIE};
    }

    @Override
    public boolean isSingular() {
        return false;
    }

}
