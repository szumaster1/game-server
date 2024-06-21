package content.global.skill.production.cooking.recipe.pie.impl;

import content.global.skill.production.cooking.recipe.pie.PieRecipe;
import core.game.node.item.Item;

/**
 * The Admiral pie.
 */
public class AdmiralPie extends PieRecipe {

    private static final Item UNCOOKED_PIE = new Item(7196);

    private static final Item SALMON = new Item(329);

    private static final Item TUNA = new Item(361);

    private static final Item POTATO = new Item(1942);

    private static final Item PART_ONE = new Item(7192);

    private static final Item PART_TWO = new Item(7194);

    @Override
    public Item getProduct() {
        return UNCOOKED_PIE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{SALMON, TUNA, POTATO};
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
