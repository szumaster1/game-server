package content.global.skill.production.crafting.plugin;

import content.global.skill.production.crafting.data.PotteryData;
import content.global.skill.production.crafting.item.FirePotteryPulse;
import content.global.skill.production.crafting.item.PotteryCraftPulse;
import cfg.consts.Items;
import cfg.consts.Scenery;
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
 * Pottery plugin.
 */
@Initializable
public final class PotteryPlugin extends UseWithHandler {

    private static final Item SOFT_CLAY = new Item(Items.SOFT_CLAY_1761);

    private static final int[] OVENS = new int[]{
        Scenery.POTTERY_OVEN_2643,
        Scenery.POTTERY_OVEN_4308,
        Scenery.POTTERY_OVEN_11601,
        Scenery.POTTERY_OVEN_34802
    };

    /**
     * Instantiates a new Pottery plugin.
     */
    public PotteryPlugin() {
        super(Items.SOFT_CLAY_1761);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        new FireOvenPlugin().newInstance(arg);
        addHandler(Scenery.POTTER_S_WHEEL_2642, OBJECT_TYPE, this);
        addHandler(Scenery.POTTERY_OVEN_2643, OBJECT_TYPE, this);
        addHandler(Scenery.POTTERY_OVEN_4308, OBJECT_TYPE, this);
        addHandler(Scenery.POTTER_S_WHEEL_4310, OBJECT_TYPE, this);
        addHandler(Scenery.POTTER_S_WHEEL_20375, OBJECT_TYPE, this);
        addHandler(Scenery.POTTER_S_WHEEL_34801, OBJECT_TYPE, this);
        addHandler(Scenery.POTTERY_OVEN_34802, OBJECT_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(final NodeUsageEvent event) {
        final Player player = event.getPlayer();
        new SkillDialogueHandler(player, SkillDialogue.FIVE_OPTION, (Object[]) getPottery(false)) {

            @Override
            public void create(final int amount, int index) {
                player.getPulseManager().run(new PotteryCraftPulse(player, event.getUsedItem(), amount, PotteryData.values()[index]));
            }

            @Override
            public int getAll(int index) {
                return player.getInventory().getAmount(SOFT_CLAY);
            }

        }.open();
        return true;
    }


    private Item[] getPottery(boolean finished) {
        final Item[] items = new Item[PotteryData.values().length];
        for (int i = 0; i < items.length; i++) {
            items[i] = finished ? PotteryData.values()[i].getProduct() : PotteryData.values()[i].getUnfinished();
        }
        return items;
    }

    /**
     * Fire oven plugin.
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
         * Fire use handler.
         */
        public final class FireUseHandler extends UseWithHandler {

            /**
             * Instantiates a new Fire use handler.
             */
            public FireUseHandler() {
                super(Items.UNFIRED_POT_1787,
                    Items.UNFIRED_PIE_DISH_1789,
                    Items.UNFIRED_BOWL_1791,
                    Items.UNFIRED_PLANT_POT_5352,
                    Items.UNFIRED_POT_LID_4438);
            }

            @Override
            public Plugin<Object> newInstance(Object arg) throws Throwable {
                addHandler(Scenery.POTTERY_OVEN_2643, OBJECT_TYPE, this);
                addHandler(Scenery.POTTERY_OVEN_4308, OBJECT_TYPE, this);
                addHandler(Scenery.POTTERY_OVEN_11601, OBJECT_TYPE, this);
                addHandler(Scenery.POTTERY_OVEN_34802, OBJECT_TYPE, this);
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
                    player.getPulseManager().run(new FirePotteryPulse(player, PotteryData.values()[index].getUnfinished(), PotteryData.values()[index], amount));
                }

                @Override
                public int getAll(int index) {
                    return player.getInventory().getAmount(PotteryData.values()[index].getUnfinished());

                }
            };
        }

    }

}
