package content.global.skill.production.cooking.recipe.topping.impl;

import content.global.skill.production.cooking.recipe.Recipe;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * The Chilli con carne.
 */
public class ChilliConCarne extends Recipe {

    private static final Item SPICY_SAUCE = new Item(7072);

    private static final Item CHILLI_CON_CARNE = new Item(7062);

    private static final Item COOKED_MEAT = new Item(2142);

    private static final Item KNIFE = new Item(946);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (player.getSkills().getLevel(Skills.COOKING) < 9) {
            player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + 9 + " in order to do this.");
            return;
        }
        if (!player.getInventory().containsItem(KNIFE)) {
            player.getDialogueInterpreter().sendDialogue("You need a knife in order to cut up the meat.");
            return;
        }
        super.mix(player, event);
        player.getSkills().addExperience(Skills.COOKING, 25, true);
    }

    @Override
    public Item getBase() {
        return SPICY_SAUCE;
    }

    @Override
    public Item getProduct() {
        return CHILLI_CON_CARNE;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{COOKED_MEAT};
    }

    @Override
    public Item[] getParts() {
        return new Item[]{};
    }

    @Override
    public String getMixMessage(NodeUsageEvent event) {
        return "You put the cut up meat into the bowl.";
    }

    @Override
    public boolean isSingular() {
        return true;
    }

}
