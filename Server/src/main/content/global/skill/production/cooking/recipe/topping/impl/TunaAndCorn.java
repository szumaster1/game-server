package content.global.skill.production.cooking.recipe.topping.impl;

import content.global.skill.production.cooking.recipe.Recipe;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * The Tuna and corn.
 */
public final class TunaAndCorn extends Recipe {

    private static final Item CHOPPED_TUNA = new Item(7086);

    private static final Item COOKED_CORN = new Item(5988);

    private static final Item TUNA_AND_CORN = new Item(7068);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (player.getSkills().getLevel(Skills.COOKING) < 67) {
            player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + 57 + " in order to do this.");
            return;
        }
        super.mix(player, event);
        player.getSkills().addExperience(Skills.COOKING, 204, true);
    }

    @Override
    public Item getBase() {
        return CHOPPED_TUNA;
    }

    @Override
    public Item getProduct() {
        return TUNA_AND_CORN;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{COOKED_CORN};
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
