package content.global.skill.production.cooking.recipe.pizza.impl;

import content.global.skill.production.cooking.recipe.pizza.PizzaRecipe;
import core.game.node.item.Item;

/**
 * The Anchovy pizza.
 */
public class AnchovyPizza extends PizzaRecipe {
    private static final Item ANCHOVY_PIZZA = new Item(2297);
    private static final Item ANCHOVIES = new Item(319);

    @Override
    public double getExperience() {
        return 39;
    }

    @Override
    public Item getProduct() {
        return ANCHOVY_PIZZA;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{ANCHOVIES};
    }

    @Override
    public int getLevel() {
        return 55;
    }

}
