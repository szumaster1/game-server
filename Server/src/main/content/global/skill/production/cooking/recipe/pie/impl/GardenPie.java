package content.global.skill.production.cooking.recipe.pie.impl;

import content.global.skill.production.cooking.recipe.pie.PieRecipe;
import core.game.node.item.Item;

/**
 * The Garden pie.
 */
public class GardenPie extends PieRecipe {

    private static final Item UNCOOKED_PIE = new Item(7176);

    private static final Item TOMATO = new Item(1982);

    private static final Item ONION = new Item(1957);

    private static final Item CABBAGE = new Item(1965);

    private static final Item PART_ONE = new Item(7172);

    private static final Item PART_TWO = new Item(7174);

    @Override
    public Item getProduct() {
        return UNCOOKED_PIE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{TOMATO, ONION, CABBAGE};
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
