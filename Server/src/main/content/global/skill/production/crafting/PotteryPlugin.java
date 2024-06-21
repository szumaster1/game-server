package content.global.skill.production.crafting;

import content.global.skill.production.crafting.pottery.FirePotteryPulse;
import content.global.skill.production.crafting.pottery.PotteryItem;
import content.global.skill.production.crafting.pottery.PotteryPulse;
import core.cache.def.impl.SceneryDefinition;
import core.game.dialogue.SkillDialogueHandler;
import core.game.dialogue.SkillDialogueHandler.SkillDialogue;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.OptionHandler;
import core.game.interaction.UseWithHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Pottery plugin.
 */
@Initializable
public final class PotteryPlugin extends UseWithHandler {

    private static final Item SOFT_CLAY = new Item(1761);

    private static final int[] OVENS = new int[]{2643, 4308, 11601, 34802};

    /**
     * Instantiates a new Pottery plugin.
     */
    public PotteryPlugin() {
        super(1761);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        new FireOvenPlugin().newInstance(arg);
        addHandler(2642, OBJECT_TYPE, this);
        addHandler(2643, OBJECT_TYPE, this);
        addHandler(4308, OBJECT_TYPE, this);
        addHandler(4310, OBJECT_TYPE, this);
        addHandler(20375, OBJECT_TYPE, this);
        addHandler(34801, OBJECT_TYPE, this);
        addHandler(34802, OBJECT_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(final NodeUsageEvent event) {
        final Player player = event.getPlayer();
        new SkillDialogueHandler(player, SkillDialogue.FIVE_OPTION, (Object[]) getPottery(false)) {

            @Override
            public void create(final int amount, int index) {
                player.getPulseManager().run(new PotteryPulse(player, event.getUsedItem(), amount, PotteryItem.values()[index]));
            }

            @Override
            public int getAll(int index) {
                return player.getInventory().getAmount(SOFT_CLAY);
            }

        }.open();
        return true;
    }


    private Item[] getPottery(boolean finished) {
        final Item[] items = new Item[PotteryItem.values().length];
        for (int i = 0; i < items.length; i++) {
            items[i] = finished ? PotteryItem.values()[i].getProduct() : PotteryItem.values()[i].getUnfinished();
        }
        return items;
    }


    /**
     * The Fire oven plugin.
     */
    public class FireOvenPlugin extends OptionHandler {

        @Override
        public Plugin<Object> newInstance(Object arg) throws Throwable {
            for (int id : OVENS) {
                SceneryDefinition.forId(id).getHandlers().put("option:fire", this);
            }
            new FireUseHandler().newInstance(arg);
            return this;
        }

        @Override
        public boolean handle(final Player player, Node node, String option) {
            getSkillHandler(player).open();
            return true;
        }


        /**
         * The Fire use handler.
         */
        public final class FireUseHandler extends UseWithHandler {


            /**
             * Instantiates a new Fire use handler.
             */
            public FireUseHandler() {
                super(1787, 1789, 1791, 5352, 4438);
            }

            @Override
            public Plugin<Object> newInstance(Object arg) throws Throwable {
                addHandler(2643, OBJECT_TYPE, this);
                addHandler(4308, OBJECT_TYPE, this);
                addHandler(11601, OBJECT_TYPE, this);
                addHandler(34802, OBJECT_TYPE, this);
                return this;
            }

            @Override
            public boolean handle(NodeUsageEvent event) {
                final Player player = event.getPlayer();
                getSkillHandler(player).open();
                return true;
            }

        }


        /**
         * Gets skill handler.
         *
         * @param player the player
         * @return the skill handler
         */
        public SkillDialogueHandler getSkillHandler(final Player player) {
            return new SkillDialogueHandler(player, SkillDialogue.FIVE_OPTION, (Object[]) getPottery(true)) {

                @Override
                public void create(final int amount, final int index) {
                    player.getPulseManager().run(new FirePotteryPulse(player, PotteryItem.values()[index].getUnfinished(), PotteryItem.values()[index], amount));
                }

                @Override
                public int getAll(int index) {
                    return player.getInventory().getAmount(PotteryItem.values()[index].getUnfinished());

                }
            };
        }

    }

}
