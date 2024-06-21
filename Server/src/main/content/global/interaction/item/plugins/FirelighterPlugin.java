package content.global.interaction.item.plugins;

import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Firelighter plugin.
 */
@Initializable
public class FirelighterPlugin extends UseWithHandler {

    private static final Item LOGS = new Item(1511);

    /**
     * Instantiates a new Firelighter plugin.
     */
    public FirelighterPlugin() {
        super(1511);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (FireLighter lighter : FireLighter.values()) {
            addHandler(lighter.getLighter().getId(), ITEM_TYPE, this);
        }
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        final FireLighter lighter = FireLighter.forLighter(event.getUsedItem().getId() == 1511 ? event.getBaseItem() : event.getUsedItem());
        if (player.getInventory().remove(lighter.getLighter(), LOGS)) {
            player.getInventory().add(lighter.getLog());
        }
        return true;
    }

    /**
     * The enum Fire lighter.
     */
    public enum FireLighter {
        /**
         * The Red.
         */
        RED(new Item(7329), new Item(7404)),
        /**
         * The Green.
         */
        GREEN(new Item(7330), new Item(7405)),
        /**
         * The Blue.
         */
        BLUE(new Item(7331), new Item(7406)),
        /**
         * The Purple.
         */
        PURPLE(new Item(10326), new Item(10329)),
        /**
         * The White.
         */
        WHITE(new Item(10327), new Item(10328));


        private final Item lighter;


        private final Item log;


        FireLighter(Item lighter, Item log) {
            this.lighter = lighter;
            this.log = log;
        }


        /**
         * Gets lighter.
         *
         * @return the lighter
         */
        public Item getLighter() {
            return lighter;
        }


        /**
         * Gets log.
         *
         * @return the log
         */
        public Item getLog() {
            return log;
        }


        /**
         * For lighter fire lighter.
         *
         * @param item the item
         * @return the fire lighter
         */
        public static FireLighter forLighter(final Item item) {
            for (FireLighter lighter : values()) {
                if (lighter.getLighter().getId() == item.getId()) {
                    return lighter;
                }
            }
            return null;
        }
    }
}
