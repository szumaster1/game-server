package content.global.skill.production.herblore.potions;

import core.game.dialogue.SkillDialogueHandler;
import core.game.dialogue.SkillDialogueHandler.SkillDialogue;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Finished potion plugin.
 */
@Initializable
public final class FinishedPotionPlugin extends UseWithHandler {

    /**
     * Instantiates a new Finished potion plugin.
     */
    public FinishedPotionPlugin() {
        super(getUnfinishedItems());
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (FinishedPotion potion : FinishedPotion.values()) {
            addHandler(potion.getIngredient().getId(), ITEM_TYPE, this);
        }
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final FinishedPotion finished = FinishedPotion.getPotion(event.getUsedItem().getName().contains("(unf)") ? event.getUsedItem() : event.getBaseItem(), event.getUsedItem().getName().contains("(unf)") ? event.getBaseItem() : event.getUsedItem());
        if (finished == null) {
            return false;
        }
        final GenericPotion potion = GenericPotion.transform(finished);
        final Player player = event.getPlayer();
        SkillDialogueHandler handler = new SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, potion.getProduct()) {
            @Override
            public void create(final int amount, int index) {
                player.getPulseManager().run(new HerblorePulse(player, potion.getBase(), amount, potion));
            }

            @Override
            public int getAll(int index) {
                return player.getInventory().getAmount(potion.getBase());
            }

        };
        if (player.getInventory().getAmount(potion.getBase()) == 1) {
            handler.create(0, 1);
        } else {
            handler.open();
        }
        return true;
    }

    /**
     * Get unfinished items int [ ].
     *
     * @return the int [ ]
     */
    public static int[] getUnfinishedItems() {
        int[] ids = new int[UnfinishedPotion.values().length];
        int counter = 0;
        for (UnfinishedPotion potion : UnfinishedPotion.values()) {
            ids[counter] = potion.getPotion().getId();
            counter++;
        }
        return ids;
    }
}
