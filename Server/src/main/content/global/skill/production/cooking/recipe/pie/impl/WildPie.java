package content.global.skill.production.cooking.recipe.pie.impl;

import content.global.skill.production.cooking.recipe.pie.PieRecipe;
import core.game.node.item.Item;

/**
 * The Wild pie.
 */
public class WildPie extends PieRecipe {

    private static final Item UNCOOKED_PIE = new Item(7206);

    private static final Item BEAR_MEAT = new Item(2136);

    private static final Item CHOMPY_MEAT = new Item(2876);

    private static final Item RABBIT_MEAT = new Item(3226);

    private static final Item PART_ONE = new Item(7202);

    private static final Item PART_TWO = new Item(7204);

    @Override
    public Item getProduct() {
        return UNCOOKED_PIE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{BEAR_MEAT, CHOMPY_MEAT, RABBIT_MEAT};
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
