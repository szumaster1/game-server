package content.global.skill.cooking.type.pizza;

import content.global.skill.cooking.type.Recipe;
import org.rs.consts.Items;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * Pizza recipe.
 */
public abstract class PizzaRecipe extends Recipe {

    /**
     * The constant PLAIN_PIZZA.
     */
    protected static final Item PLAIN_PIZZA = new Item(Items.PLAIN_PIZZA_2289);

    /**
     * Gets experience.
     *
     * @return the experience
     */
    public abstract double getExperience();

    /**
     * Gets level.
     *
     * @return the level
     */
    public abstract int getLevel();

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (player.getSkills().getLevel(Skills.COOKING) < getLevel()) {
            player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + getLevel() + " in order to do this.");
            return;
        }
        super.singleMix(player, event);
        player.getSkills().addExperience(Skills.COOKING, getExperience(), true);
    }

    @Override
    public Item[] getParts() {
        return new Item[]{};
    }

    @Override
    public Item getBase() {
        return PLAIN_PIZZA;
    }

    @Override
    public String getMixMessage(final NodeUsageEvent event) {
        return "You add " + event.getBaseItem().getName().toLowerCase() + " to the pizza.";
    }

    @Override
    public boolean isSingular() {
        return true;
    }

}
