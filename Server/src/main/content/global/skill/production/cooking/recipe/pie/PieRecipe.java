package content.global.skill.production.cooking.recipe.pie;

import content.global.skill.production.cooking.recipe.Recipe;
import core.api.consts.Items;
import core.game.interaction.NodeUsageEvent;
import core.game.node.item.Item;

public abstract class PieRecipe extends Recipe {

    protected static final Item PIE_SHELL = new Item(Items.PIE_SHELL_2315);

    @Override
    public Item[] getParts() {
        return new Item[]{};
    }

    @Override
    public Item getBase() {
        return PIE_SHELL;
    }

    @Override
    public String getMixMessage(final NodeUsageEvent event) {
        return "You fill the pie with " + (event.getBaseItem().getId() == Items.PIE_SHELL_2315 ? event.getUsedItem().getName().toLowerCase() : event.getBaseItem().getName().toLowerCase()) + ".";
    }

    @Override
    public boolean isSingular() {
        return true;
    }
}
