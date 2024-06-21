package content.global.skill.production.cooking.recipe.topping.impl;

import content.global.skill.production.cooking.recipe.Recipe;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * The Spicy sauce.
 */
public class SpicySauce extends Recipe {

    private static final Item SPICY_SAUCE = new Item(7072);

    private static final Item BOWL = new Item(1923);

    private static final Item GARLIC = new Item(1550);

    private static final Item CHOPPED_GARLIC = new Item(7074);

    private static final Item GNOME_SPICE = new Item(2169);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (player.getSkills().getLevel(Skills.COOKING) < 9) {
            player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + 9 + " in order to do this.");
            return;
        }
        super.mix(player, event);
        if (event.getBaseItem().getId() == GNOME_SPICE.getId() || event.getUsedItem().getId() == GNOME_SPICE.getId()) {
            player.getSkills().addExperience(Skills.COOKING, 25, true);
        }
    }

    @Override
    public Item getBase() {
        return BOWL;
    }

    @Override
    public Item getProduct() {
        return SPICY_SAUCE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{GARLIC, GNOME_SPICE};
    }

    @Override
    public Item[] getParts() {
        return new Item[]{BOWL, CHOPPED_GARLIC, SPICY_SAUCE};
    }

    @Override
    public String getMixMessage(NodeUsageEvent event) {
        return null;
    }

    @Override
    public boolean isSingular() {
        return false;
    }

}
