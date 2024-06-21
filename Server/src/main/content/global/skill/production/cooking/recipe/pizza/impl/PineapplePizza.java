package content.global.skill.production.cooking.recipe.pizza.impl;

import content.global.skill.production.cooking.recipe.pizza.PizzaRecipe;
import core.game.interaction.NodeUsageEvent;
import core.game.node.item.Item;

/**
 * The Pineapple pizza.
 */
public class PineapplePizza extends PizzaRecipe {
    private static final Item PINEAPPLE_PIZZA = new Item(2301);
    private static final Item PINEAPPLE_RING = new Item(2118);
    private static final Item PINEAPPLE_CHUNKS = new Item(2116);

    @Override
    public double getExperience() {
        return 52;
    }

    @Override
    public int getLevel() {
        return 65;
    }

    @Override
    public Item getProduct() {
        return PINEAPPLE_PIZZA;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{PINEAPPLE_CHUNKS, PINEAPPLE_RING};
    }

    @Override
    public String getMixMessage(NodeUsageEvent event) {
        return "You add the " + event.getBaseItem().getName().toLowerCase() + " to the pizza.";
    }

}
