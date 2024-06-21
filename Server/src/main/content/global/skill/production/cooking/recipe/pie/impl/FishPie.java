package content.global.skill.production.cooking.recipe.pie.impl;

import content.global.skill.production.cooking.recipe.pie.PieRecipe;
import core.game.node.item.Item;

/**
 * The Fish pie.
 */
public class FishPie extends PieRecipe {

    private static final Item UNCOOKED_PIE = new Item(7186);

    private static final Item TROUT = new Item(333);

    private static final Item COD = new Item(339);

    private static final Item POTATO = new Item(1942);

    private static final Item PART_ONE = new Item(7182);

    private static final Item PART_TWO = new Item(7184);

    @Override
    public Item getProduct() {
        return UNCOOKED_PIE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{TROUT, COD, POTATO};
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
