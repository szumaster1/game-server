package content.global.skill.production.cooking.recipe.topping.impl;

import content.global.skill.production.cooking.recipe.topping.ToppingRecipe;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;

/**
 * The Sliced mushroom.
 */
public final class SlicedMushroom extends ToppingRecipe {

    private static final Item SLICED_MUSHROOMS = new Item(7080);

    private static final Item MUSHROOM = new Item(6004);

    private static final Item KNIFE = new Item(946);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (!player.getInventory().containsItem(KNIFE)) {
            player.getDialogueInterpreter().sendDialogue("You need a knife in order to slice up the mushrooms.");
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
        return 0;
    }

    @Override
    public Item getProduct() {
        return SLICED_MUSHROOMS;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{MUSHROOM};
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
