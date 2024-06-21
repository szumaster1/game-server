package content.global.skill.production.cooking.recipe.topping.impl;

import content.global.skill.production.cooking.recipe.Recipe;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * The Egg and tomato.
 */
public class EggAndTomato extends Recipe {

    private static final Item EGG_AND_TOMATO = new Item(7064);

    private static final Item SCRAMBLED_EGG = new Item(7078);

    private static final Item TOMATO = new Item(1982);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (player.getSkills().getLevel(Skills.COOKING) < 23) {
            player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + 23 + " in order to do this.");
            return;
        }
        super.mix(player, event);
        player.getSkills().addExperience(Skills.COOKING, 50, true);
    }

    @Override
    public Item getBase() {
        return SCRAMBLED_EGG;
    }

    @Override
    public Item getProduct() {
        return EGG_AND_TOMATO;
    }

    @Override
    public Item[] getIngredients() {
        return new Item[]{TOMATO};
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
