package content.global.skill.production.cooking.recipe.pizza.impl;

import content.global.skill.production.cooking.recipe.pizza.PizzaRecipe;
import core.game.node.item.Item;

/**
 * The Meat pizza.
 */
public class MeatPizza extends PizzaRecipe {

    private static final Item MEAT_PIZZA = new Item(2293);

    private static final Item COOKED_MEAT = new Item(2142);

    private static final Item COOKED_CHICKEN = new Item(2140);

    @Override
    public double getExperience() {
        return 26;
    }

    @Override
    public int getLevel() {
        return 45;
    }

    @Override
    public Item getProduct() {
        return MEAT_PIZZA;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{COOKED_MEAT, COOKED_CHICKEN};
    }

}
