package content.global.skill.production.cooking.recipe.pie.impl;

import content.global.skill.production.cooking.recipe.pie.PieRecipe;
import core.game.node.item.Item;

/**
 * The Mud pie.
 */
public class MudPie extends PieRecipe {
    private static final Item UNCOOKED_PIE = new Item(7168);
    private static final Item COMPOST = new Item(6032);
    private static final Item BUCKET_OF_WATER = new Item(1929);
    private static final Item CLAY = new Item(434);
    private static final Item PART_ONE = new Item(7164);
    private static final Item PART_TWO = new Item(7166);

    @Override
    public Item getProduct() {
        return UNCOOKED_PIE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{COMPOST, BUCKET_OF_WATER, CLAY};
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
