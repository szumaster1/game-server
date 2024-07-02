package content.global.skill.production.cooking.recipe.topping;

import content.global.skill.production.cooking.recipe.Recipe;
import core.api.consts.Items;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

public abstract class ToppingRecipe extends Recipe {

    protected static final Item BOWL = new Item(Items.BOWL_1923);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (player.getSkills().getLevel(Skills.COOKING) < getLevel()) {
            player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + getLevel() + " in order to do this.");
            return;
        }
        super.mix(player, event);
        player.getSkills().addExperience(Skills.COOKING, getExperience(), true);
    }

    @Override
    public Item getBase() {
        return BOWL;
    }

    @Override
    public String getMixMessage(NodeUsageEvent event) {
        return null;
    }

    @Override
    public boolean isSingular() {
        return true;
    }

    public abstract int getLevel();

    public abstract double getExperience();

}
