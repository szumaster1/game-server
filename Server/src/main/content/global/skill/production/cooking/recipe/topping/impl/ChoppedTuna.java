package content.global.skill.production.cooking.recipe.topping.impl;

import content.global.skill.production.cooking.recipe.topping.ToppingRecipe;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;

/**
 * The Chopped tuna.
 */
public class ChoppedTuna extends ToppingRecipe {

    private static final Item CHOPPED_TUNA = new Item(7086);

    private static final Item TUNA = new Item(361);

    private static final Item KNIFE = new Item(946);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (!player.getInventory().containsItem(KNIFE)) {
            player.getDialogueInterpreter().sendDialogue("You need a knife in order to slice up the tuna.");
            return;
        }
        super.mix(player, event);
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public double getExperience() {
        return 1;
    }

    @Override
    public Item getProduct() {
        return CHOPPED_TUNA;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{TUNA};
    }

    @Override
    public Item[] getParts() {
        return new Item[]{};
    }

    @Override
    public boolean isSingular() {
        return true;
    }
}
