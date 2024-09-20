package content.global.skill.cooking.recipe.potato;

import content.global.skill.cooking.recipe.Recipe;
import org.rs.consts.Items;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * Potato recipe.
 */
public abstract class PotatoRecipe extends Recipe {

    /**
     * The constant BOWL.
     */
    protected static final Item BOWL = new Item(Items.BOWL_1923);
    private static final Item POTATO_WITH_BUTTER = new Item(Items.POTATO_WITH_BUTTER_6703);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
        if (player.getSkills().getLevel(Skills.COOKING) < getLevel()) {
            player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + getLevel() + " in order to do this.");
            return;
        }
        super.singleMix(player, event);
        if (isTopping()) {
            player.getInventory().add(BOWL);
        }
        player.getSkills().addExperience(Skills.COOKING, getExperience(), true);
    }

    @Override
    public Item getBase() {
        return POTATO_WITH_BUTTER;
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

    /**
     * Is topping boolean.
     *
     * @return the boolean
     */
    public abstract boolean isTopping();

    /**
     * Gets level.
     *
     * @return the level
     */
    public abstract int getLevel();

    /**
     * Gets experience.
     *
     * @return the experience
     */
    public abstract double getExperience();
}
