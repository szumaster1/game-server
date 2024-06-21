package content.global.skill.production.crafting.armour;

import core.game.component.Component;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;

/**
 * The Leather crafting.
 */
public final class LeatherCrafting {

    /**
     * The constant NEEDLE.
     */
    public static final int NEEDLE = 1733;
    /**
     * The constant THREAD.
     */
    public static final Item THREAD = new Item(1734, 1);
    /**
     * The constant LEATHER.
     */
    public static final int LEATHER = 1741;
    /**
     * The constant HARD_LEATHER.
     */
    public static final int HARD_LEATHER = 1743;
    /**
     * The constant GREEN_LEATHER.
     */
    public static final int GREEN_LEATHER = 1745, /**
     * The Blue leather.
     */
    BLUE_LEATHER = 2505, /**
     * The Red leather.
     */
    RED_LEATHER = 2507, /**
     * The Black leather.
     */
    BLACK_LEATHER = 2509;
    private static final Component COMPONENT = new Component(154);

    /**
     * Is last thread boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public static boolean isLastThread(final Player player) {
        final Item thread = getThread(player);
        if (thread == null) {
            return false;
        }
        int charge = thread.getCharge();
        return charge >= 1004;
    }

    /**
     * Decay thread.
     *
     * @param player the player
     */
    public static void decayThread(final Player player) {
        final Item thread = getThread(player);
        if (thread == null) {
            return;
        }
        int charge = thread.getCharge();
        thread.setCharge(charge + 1);
    }

    /**
     * Remove thread.
     *
     * @param player the player
     */
    public static void removeThread(final Player player) {
        if (player.getInventory().remove(THREAD)) {
            player.getPacketDispatch().sendMessage("You use a reel of your thread.");
            Item thread = getThread(player);
            if (thread != null) {
                thread.setCharge(1000);
            }
        }
    }

    /**
     * Gets thread.
     *
     * @param player the player
     * @return the thread
     */
    public static Item getThread(final Player player) {
        return player.getInventory().get(player.getInventory().getSlot(THREAD));
    }

    /**
     * The enum Dragon hide.
     */
    public enum DragonHide {
        /**
         * Green d hide vambs dragon hide.
         */
        GREEN_D_HIDE_VAMBS(1745, 1, 1065, 57, 62),
        /**
         * Green d hide chaps dragon hide.
         */
        GREEN_D_HIDE_CHAPS(1745, 2, 1099, 60, 124),
        /**
         * Green d hide body dragon hide.
         */
        GREEN_D_HIDE_BODY(1745, 3, 1135, 63, 186),
        /**
         * Blue d hide vambs dragon hide.
         */
        BLUE_D_HIDE_VAMBS(2505, 1, 2487, 66, 70),
        /**
         * Blue d hide chaps dragon hide.
         */
        BLUE_D_HIDE_CHAPS(2505, 2, 2493, 68, 140),
        /**
         * Blue d hide body dragon hide.
         */
        BLUE_D_HIDE_BODY(2505, 3, 2499, 71, 210),
        /**
         * Red d hide vambs dragon hide.
         */
        RED_D_HIDE_VAMBS(2507, 1, 2489, 73, 78),
        /**
         * Red d hide chaps dragon hide.
         */
        RED_D_HIDE_CHAPS(2507, 2, 2495, 75, 156),
        /**
         * Red d hide body dragon hide.
         */
        RED_D_HIDE_BODY(2507, 3, 2501, 77, 234),
        /**
         * Black d hide vambs dragon hide.
         */
        BLACK_D_HIDE_VAMBS(2509, 1, 2491, 79, 86),
        /**
         * Black d hide chaps dragon hide.
         */
        BLACK_D_HIDE_CHAPS(2509, 2, 2497, 82, 172),
        /**
         * Black d hide body dragon hide.
         */
        BLACK_D_HIDE_BODY(2509, 3, 2503, 84, 258);


        DragonHide(int leather, int amount, int product, int level, double experience) {
            this.leather = leather;
            this.amount = amount;
            this.product = product;
            this.level = level;
            this.experience = experience;
        }

        private final int leather;
        private final int amount;
        private final int product;
        private final int level;
        private final double experience;

        /**
         * Gets leather.
         *
         * @return the leather
         */
        public int getLeather() {
            return leather;
        }

        /**
         * Gets amount.
         *
         * @return the amount
         */
        public int getAmount() {
            return amount;
        }

        /**
         * Gets product.
         *
         * @return the product
         */
        public int getProduct() {
            return product;
        }

        /**
         * Gets level.
         *
         * @return the level
         */
        public int getLevel() {
            return level;
        }

        /**
         * Gets experience.
         *
         * @return the experience
         */
        public double getExperience() {
            return experience;
        }


        /**
         * For leather dragon hide.
         *
         * @param leather the leather
         * @return the dragon hide
         */
        public static DragonHide forLeather(int leather) {
            for (DragonHide hide : DragonHide.values()) {
                if (hide.getLeather() == leather) {
                    return hide;
                }
            }
            return null;
        }
    }

    /**
     * The enum Soft leather.
     */
    public enum SoftLeather {
        /**
         * The Armour.
         */
        ARMOUR(28, 14, 25, new Item(1129)),
        /**
         * The Gloves.
         */
        GLOVES(29, 1, 13.8, new Item(1059)),
        /**
         * The Boots.
         */
        BOOTS(30, 7, 16.3, new Item(1061)),
        /**
         * The Vambraces.
         */
        VAMBRACES(31, 11, 22, new Item(1063)),
        /**
         * The Chaps.
         */
        CHAPS(32, 18, 27, new Item(1095)),
        /**
         * The Coif.
         */
        COIF(33, 38, 37, new Item(1169)),
        /**
         * The Cowl.
         */
        COWL(34, 9, 18.5, new Item(1167));


        SoftLeather(int button, int level, double experience, Item product) {
            this.button = button;
            this.level = level;
            this.experience = experience;
            this.product = product;
        }


        private final int button;


        private final int level;


        private final double experience;


        private final Item product;


        /**
         * Open.
         *
         * @param player the player
         */
        public static void open(final Player player) {
            player.getInterfaceManager().open(COMPONENT);
        }


        /**
         * Gets level.
         *
         * @return the level
         */
        public int getLevel() {
            return level;
        }


        /**
         * Gets experience.
         *
         * @return the experience
         */
        public double getExperience() {
            return experience;
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
         * Gets button.
         *
         * @return the button
         */
        public int getButton() {
            return button;
        }


        /**
         * For button soft leather.
         *
         * @param button the button
         * @return the soft leather
         */
        public static SoftLeather forButton(int button) {
            for (SoftLeather soft : SoftLeather.values()) {
                if (soft.getButton() == button) {
                    return soft;
                }
            }
            return null;
        }
    }
}
