package content.global.skill.production.cooking.recipe.topping.impl;

import content.global.skill.production.cooking.recipe.Recipe;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * The Mushroom and onion.
 */
public class MushroomAndOnion extends Recipe {

    private static final Item MUSHROOM_AND_ONION = new Item(7066);

    private static final Item FRIED_ONIONS = new Item(7084);

    private static final Item FRIED_MUSHROOMS = new Item(7082);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (player.getSkills().getLevel(Skills.COOKING) < 57) {
            player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + 57 + " in order to do this.");
            return;
        }
        super.mix(player, event);
        player.getSkills().addExperience(Skills.COOKING, 120, true);
    }

    @Override
    public Item getBase() {
        return FRIED_MUSHROOMS;
    }

    @Override
    public Item getProduct() {
        return MUSHROOM_AND_ONION;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{FRIED_ONIONS};
    }

    @Override
    public Item[] getParts() {
        return new Item[]{};
    }

    @Override
    public String getMixMessage(NodeUsageEvent event) {
        return null;
    }

    @Override
    public boolean isSingular() {
        return true;
    }

}
