package content.global.interaction.item.plugins;

import core.api.consts.Items;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

import java.util.Arrays;

/**
 * The Fishfood plugin.
 */
@Initializable
public final class FishfoodPlugin extends UseWithHandler {
    private static final int FISH_FOOD = Items.FISH_FOOD_272;
    private static final int POISON = Items.POISON_273;
    private static final int POISONED_FISH_FOOD = Items.POISONED_FISH_FOOD_274;

    /**
     * The enum Fish food uses.
     */
    protected enum FishFoodUses {
        /**
         * The Poisoned.
         */
        POISONED(POISON, FISH_FOOD, POISONED_FISH_FOOD, "You poison the fish food."),
        /**
         * The Guambox.
         */
        GUAMBOX(Items.GROUND_GUAM_6681, Items.AN_EMPTY_BOX_6675, Items.GUAM_IN_A_BOX_6677, "You put the ground Guam into the box."),
        /**
         * The Seaweedbox.
         */
        SEAWEEDBOX(Items.GROUND_SEAWEED_6683, Items.AN_EMPTY_BOX_6675, Items.SEAWEED_IN_A_BOX_6679, "You put the ground Seaweed into the box."),
        /**
         * The Food 1.
         */
        FOOD1(Items.GROUND_SEAWEED_6683, Items.GUAM_IN_A_BOX_6677, FISH_FOOD, "You put the ground Seaweed into the box and make Fish Food."),
        /**
         * The Food 2.
         */
        FOOD2(Items.GROUND_GUAM_6681, Items.SEAWEED_IN_A_BOX_6679, FISH_FOOD, "You put the ground Guam into the box and make Fish Food."),
        /**
         * The Fishbowl.
         */
        FISHBOWL(Items.FISHBOWL_6668, Items.SEAWEED_401, Items.FISHBOWL_6669, "You place the seaweed in the bowl.");


        private final int used;
        private final int with;
        private final int product;
        private final String msg;

        /**
         * The Usables.
         */
        static protected int[] usables = Arrays.stream(FishFoodUses.values()).mapToInt(v -> v.used).toArray();

        FishFoodUses(int used, int with, int product, String msg) {
            this.used = used;
            this.with = with;
            this.product = product;
            this.msg = msg;
        }

        /**
         * Product for item.
         *
         * @param used the used
         * @param with the with
         * @return the item
         */
        protected static Item productFor(int used, int with) {
            for (FishFoodUses value : values()) {
                if (value.used == used && value.with == with) {
                    return new Item(value.product);
                }
            }
            return null;
        }

        /**
         * Msg for string.
         *
         * @param used the used
         * @param with the with
         * @return the string
         */
        protected static String msgFor(int used, int with) {
            for (FishFoodUses value : values()) {
                if (value.used == used && value.with == with) {
                    return value.msg;
                }
            }
            return null;
        }
    }


    /**
     * Instantiates a new Fishfood plugin.
     */
    public FishfoodPlugin() {
        super(FishFoodUses.usables);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (FishFoodUses value : FishFoodUses.values()) {
            addHandler(value.with, ITEM_TYPE, this);
        }
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        int used = event.getUsedItem().getId();
        int with = event.getBaseItem().getId();
        Item product = FishFoodUses.productFor(used, with);

        player.getPulseManager().run(new Pulse(1, player) {
            @Override
            public boolean pulse() {
                if (player.getInventory().remove(new Item(with), new Item(used))) {
                    player.animate(new Animation(1309));
                    player.getPacketDispatch().sendMessage(FishFoodUses.msgFor(used, with));
                    player.getInventory().add(product);
                    player.lock(2);
                }
                return true;
            }

            @Override
            public void stop() {
                super.stop();
            }
        });

        return true;
    }

}