package content.region.asgarnia.quest.diplomacy.plugin;

import core.cache.def.impl.ItemDefinition;
import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.OptionHandler;
import core.game.interaction.UseWithHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.plugin.ClassScanner;
import core.plugin.Plugin;
import core.tools.StringUtils;

import static core.api.ContentAPIKt.setAttribute;

/**
 * The Goblin diplomacy plugin.
 */
public final class GoblinDiplomacyPlugin extends OptionHandler {
    private static final Item GOBLIN_MAIL = new Item(288);
    private static final int[] CRATES = new int[]{16557, 16561, 16560, 16559};

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.forId(288).getHandlers().put("option:wear", this);
        for (int object : CRATES) {
            SceneryDefinition.forId(object).getHandlers().put("option:search", this);
        }
        for (GoblinMailPlugin.GoblinMail mail : GoblinMailPlugin.GoblinMail.values()) {
            ItemDefinition.forId(mail.getProduct().getId()).getHandlers().put("option:wear", this);
        }
        ClassScanner.definePlugin(new GoblinMailPlugin());
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        int id = node instanceof Item ? node.getId() : node.getId();
        switch (option) {
            case "wear":
                player.getPacketDispatch().sendMessage("That armour is to small for a human.");
                break;
        }
        switch (id) {
            case 16559:
            case 16557:
            case 16561:
            case 16560:
                if (player.getAttribute("crate:" + id, 0) < GameWorld.getTicks()) {
                    setAttribute(player, "crate:" + id, GameWorld.getTicks() + 500);
                    if (!player.getInventory().add(GOBLIN_MAIL)) {
                        GroundItemManager.create(GOBLIN_MAIL, player);
                    }
                    player.getDialogueInterpreter().sendItemMessage(GOBLIN_MAIL.getId(), "You find some goblin armour.");
                    return true;
                }
                player.getPacketDispatch().sendMessage("You search the crate but find nothing.");
                break;
        }
        return true;
    }

    @Override
    public boolean isWalk() {
        return false;
    }

    @Override
    public boolean isWalk(final Player player, final Node node) {
        return !(node instanceof Item);
    }

    /**
     * The Goblin mail plugin.
     */
    public static final class GoblinMailPlugin extends UseWithHandler {


        /**
         * Instantiates a new Goblin mail plugin.
         */
        public GoblinMailPlugin() {
            super(1763, 1769, 1765, 1771, 1767, 1773, 4622, 11808, 6955);
        }

        @Override
        public Plugin<Object> newInstance(Object arg) throws Throwable {
            addHandler(288, ITEM_TYPE, this);
            int[] ids = new int[]{1763, 1769, 1765, 1771, 1767, 1773, 4622, 11808, 6955};
            for (int i : ids) {
                addHandler(i, ITEM_TYPE, this);
            }
            return this;
        }

        @Override
        public boolean handle(NodeUsageEvent event) {
            final Player player = event.getPlayer();
            final GoblinMail mail = GoblinMail.forItem(event.getUsedItem());
            final Dyes dye = Dyes.forItem(event.getUsedItem(), event.getBaseItem());
            if (dye != null && (event.getUsedItem().getId() != 288 || event.getBaseItem().getId() != 288)) {
                handleDyeMix(player, dye, event);
                return true;
            }
            if (mail == null || (event.getUsedItem().getId() != 288 && event.getBaseItem().getId() != 288)) {
                return false;
            }
            if (player.getInventory().remove(mail.getDye(), event.getBaseItem())) {
                player.getInventory().add(mail.getProduct());
                player.getPacketDispatch().sendMessage("You dye the goblin armour " + mail.name().toLowerCase() + ".");
            }
            return true;
        }


        /**
         * Handle dye mix.
         *
         * @param player the player
         * @param dye    the dye
         * @param event  the event
         */
        public void handleDyeMix(final Player player, Dyes dye, NodeUsageEvent event) {
            if (dye == null) {
                player.getPacketDispatch().sendMessage("Those dyes dont mix together.");
                return;
            }
            if (player.getInventory().remove(dye.getMaterials())) {
                player.getInventory().add(dye.getProduct());
            }
            player.getPacketDispatch().sendMessage("You mix the two dyes and make " + (StringUtils.isPlusN(dye.getProduct().getName().toLowerCase().replace("dye", "").trim()) ? "an " : "a ") + dye.getProduct().getName().toLowerCase().replace("dye", "").trim() + " one.");

        }


        /**
         * The enum Goblin mail.
         */
        public enum GoblinMail {
            /**
             * The Red.
             */
            RED(new Item(1763), new Item(9054)),
            /**
             * The Orange.
             */
            ORANGE(new Item(1769), new Item(286)),
            /**
             * The Yellow.
             */
            YELLOW(new Item(1765), new Item(9056)),
            /**
             * The Green.
             */
            GREEN(new Item(1771), new Item(9057)),
            /**
             * The Blue.
             */
            BLUE(new Item(1767), new Item(287)),
            /**
             * The Purple.
             */
            PURPLE(new Item(1773), new Item(9058)),
            /**
             * The Black.
             */
            BLACK(new Item(4622), new Item(9055)),
            /**
             * The White.
             */
            WHITE(new Item(11808), new Item(11791)),
            /**
             * The Pink.
             */
            PINK(new Item(6955), new Item(9059));


            private final Item dye;


            private final Item product;


            GoblinMail(Item dye, Item product) {
                this.dye = dye;
                this.product = product;
            }


            /**
             * Gets dye.
             *
             * @return the dye
             */
            public Item getDye() {
                return dye;
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
             * For item goblin mail.
             *
             * @param item the item
             * @return the goblin mail
             */
            public static GoblinMail forItem(final Item item) {
                for (GoblinMail mail : GoblinMail.values()) {
                    if (mail.getDye().getId() == item.getId()) {
                        return mail;
                    }
                }
                return null;
            }
        }


        /**
         * The enum Dyes.
         */
        public enum Dyes {
            /**
             * The Orange.
             */
            ORANGE(new Item(1769), new Item(1763), new Item(1765)),
            /**
             * The Green.
             */
            GREEN(new Item(1771), new Item(1765), new Item(1767)),
            /**
             * The Purple.
             */
            PURPLE(new Item(1773), new Item(1767), new Item(1763));


            Dyes(Item product, Item... materials) {
                this.product = product;
                this.materials = materials;
            }


            private final Item product;


            private final Item[] materials;


            /**
             * Gets product.
             *
             * @return the product
             */
            public Item getProduct() {
                return product;
            }


            /**
             * Get materials item [ ].
             *
             * @return the item [ ]
             */
            public Item[] getMaterials() {
                return materials;
            }


            /**
             * For item dyes.
             *
             * @param item   the item
             * @param second the second
             * @return the dyes
             */
            public static Dyes forItem(final Item item, final Item second) {
                for (Dyes dye : Dyes.values()) {
                    if (dye.getMaterials()[0].getId() == item.getId() && dye.getMaterials()[1].getId() == second.getId() || dye.getMaterials()[0].getId() == second.getId() && dye.getMaterials()[1].getId() == item.getId()) {
                        return dye;
                    }
                }
                return null;
            }
        }

    }
}
