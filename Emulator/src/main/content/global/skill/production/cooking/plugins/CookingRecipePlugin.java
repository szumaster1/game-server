package content.global.skill.production.cooking.plugins;

import content.global.skill.production.cooking.recipe.Recipe;
import core.game.dialogue.SkillDialogueHandler;
import core.game.dialogue.SkillDialogueHandler.SkillDialogue;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.plugin.Initializable;
import core.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;


/**
 * Cooking recipe plugin.
 */
@Initializable
public final class CookingRecipePlugin extends UseWithHandler {


    /**
     * Instantiates a new Cooking recipe plugin.
     */
    public CookingRecipePlugin() {
        super(getAllowedNodes());
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (Recipe recipe : Recipe.RECIPES) {
            for (Item ingredient : recipe.getIngredients()) {
                addHandler(ingredient.getId(), ITEM_TYPE, this);
            }
        }
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        Recipe recipe = null;

        for (Recipe temp : Recipe.RECIPES) {
            if (temp.isSingular()) {
                if (temp.getBase().getId() == event.getUsedItem().getId() || temp.getBase().getId() == event.getBaseItem().getId()) {
                    for (Item ingredient : temp.getIngredients()) {
                        if (ingredient.getId() == event.getBaseItem().getId() || ingredient.getId() == event.getUsedItem().getId()) {
                            recipe = temp;
                            break;
                        }
                    }
                }
            } else {
                Item part = null;
                Item ingredient = null;
                for (int k = 0; k < temp.getParts().length; k++) {
                    for (int i = 0; i < temp.getIngredients().length; i++) {
                        part = temp.getParts()[k];
                        ingredient = temp.getIngredients()[i];
                        if (part.getId() == event.getUsedItem().getId() && ingredient.getId() == event.getBaseItem().getId() || part.getId() == event.getBaseItem().getId() && ingredient.getId() == event.getUsedItem().getId()) {
                            if (k == i) {
                                /*
                                 * represents that this ingredient can
                                 * mix with the other.
                                 */
                                recipe = temp;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (recipe != null) {
            final Player player = event.getPlayer();
            final Recipe recipe_ = recipe;
            SkillDialogueHandler handler = new SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, recipe.getProduct()) {
                @Override
                public void create(final int amount, int index) {
                    player.getPulseManager().run(new Pulse(2) {
                        int count = 0;

                        @Override
                        public boolean pulse() {
                            recipe_.mix(player, event);
                            return ++count >= amount;
                        }
                    });
                }

                @Override
                public int getAll(int index) {
                    return player.getInventory().getAmount(recipe_.getBase());
                }
            };
            if (player.getInventory().getAmount(recipe.getBase()) == 1) {
                recipe_.mix(player, event);
            } else {
                handler.open();
            }
            return true;
        }
        return false;
    }

    private static int[] getAllowedNodes() {
        List<Integer> bases = new ArrayList<>(10);
        for (Recipe recipe : Recipe.RECIPES) {
            for (Item base : recipe.getParts()) {
                if (bases.contains(base.getId())) {
                    continue;
                }
                bases.add(base.getId());
            }
            if (bases.contains(recipe.getBase().getId())) {
                continue;
            }
            bases.add(recipe.getBase().getId());
        }
        int[] baseArray = new int[bases.size()];
        for (int i = 0; i < bases.size(); i++) {
            baseArray[i] = bases.get(i);
        }
        return baseArray;
    }

}
