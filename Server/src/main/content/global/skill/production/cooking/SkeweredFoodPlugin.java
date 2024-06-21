package content.global.skill.production.cooking;

import core.api.consts.Items;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Skewered food plugin.
 */
@Initializable
public class SkeweredFoodPlugin extends UseWithHandler {

    private final int LEVEL = 20;

    /**
     * Instantiates a new Skewered food plugin.
     */
    public SkeweredFoodPlugin() {
        super(7225);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (SkeweredSet set : SkeweredSet.values()) {
            addHandler(set.getRaw().getId(), ITEM_TYPE, this);
        }
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        if (player.getSkills().getLevel(Skills.FIREMAKING) < LEVEL) {
            player.getPacketDispatch().sendMessage("You meed a Firemaking level of at least " + LEVEL + " in order to do this.");
            return true;
        }
        final SkeweredSet set = SkeweredSet.forItem(event.getBaseItem().getId() == 7225 ? event.getUsedItem() : event.getBaseItem());
        if (player.getInventory().remove(event.getBaseItem()) && player.getInventory().remove(event.getUsedItem())) {
            player.getInventory().add(set.getProduct());
        }
        return true;
    }

    /**
     * The enum Skewered set.
     */
    public enum SkeweredSet {
        /**
         * The Chompy.
         */
        CHOMPY(new Item(Items.RAW_CHOMPY_2876), new Item(Items.SKEWERED_CHOMPY_7230)),
        /**
         * The Rabbit.
         */
        RABBIT(new Item(Items.RAW_RABBIT_3226), new Item(Items.SKEWERED_RABBIT_7224)),
        /**
         * The Bird.
         */
        BIRD(new Item(Items.RAW_BIRD_MEAT_9978), new Item(Items.SKEWERED_BIRD_MEAT_9984)),
        /**
         * The Beast.
         */
        BEAST(new Item(Items.RAW_BEAST_MEAT_9986), new Item(Items.SKEWERED_BEAST_9992));


        private final Item raw;


        private final Item product;


        SkeweredSet(Item raw, Item product) {
            this.raw = raw;
            this.product = product;
        }


        /**
         * Gets raw.
         *
         * @return the raw
         */
        public Item getRaw() {
            return raw;
        }


        /**
         * Gets product.
         *
         * @return the product
         */
        public Item getProduct() {
            return product;
        }


        /**
         * For item skewered set.
         *
         * @param item the item
         * @return the skewered set
         */
        public static SkeweredSet forItem(final Item item) {
            for (SkeweredSet set : values()) {
                if (set.getRaw().getId() == item.getId()) {
                    return set;
                }
            }
            return null;
        }
    }
}
