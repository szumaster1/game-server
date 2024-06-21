package content.global.skill.production.crafting;

import core.cache.def.impl.ItemDefinition;
import core.game.component.Component;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.item.Item;

/**
 * The enum Tanning product.
 */
public enum TanningProduct {
    /**
     * Soft leather tanning product.
     */
    SOFT_LEATHER(1, 1739, 1741),
    /**
     * Hard leather tanning product.
     */
    HARD_LEATHER(2, 1739, 1743),
    /**
     * Snakeskin tanning product.
     */
    SNAKESKIN(3, 6287, 6289),
    /**
     * Snakeskin 2 tanning product.
     */
    SNAKESKIN2(4, 7801, 6289),
    /**
     * Green dhide tanning product.
     */
    GREEN_DHIDE(5, 1753, 1745),
    /**
     * Bluedhide tanning product.
     */
    BLUEDHIDE(6, 1751, 2505),
    /**
     * Reddhide tanning product.
     */
    REDDHIDE(7, 1749, 2507),
    /**
     * Blackdhide tanning product.
     */
    BLACKDHIDE(8, 1747, 2509);

    private final int button;

    private final int item;

    private final int product;


    TanningProduct(int button, int item, int product) {
        this.button = button;
        this.item = item;
        this.product = product;
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
     * Gets item.
     *
     * @return the item
     */
    public int getItem() {
        return item;
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
     * For id tanning product.
     *
     * @param id the id
     * @return the tanning product
     */
    public static TanningProduct forId(int id) {
        for (TanningProduct def : TanningProduct.values()) {
            if (def.getButton() == id) {
                return def;
            }
        }
        return null;
    }

    /**
     * For item id tanning product.
     *
     * @param id the id
     * @return the tanning product
     */
    public static TanningProduct forItemId(int id) {
        for (TanningProduct def : TanningProduct.values()) {
            if (def.getItem() == id) {
                return def;
            }
        }
        return null;
    }

    /**
     * Open.
     *
     * @param player the player
     * @param npc    the npc
     */
    public static void open(final Player player, final int npc) {
        player.getInterfaceManager().open(new Component(324));
        //Removed all the string modification that was here earlier -- it seems the client automatically does it now.
    }

    /**
     * Tan.
     *
     * @param player the player
     * @param amount the amount
     * @param def    the def
     */
    public static void tan(final Player player, int amount, TanningProduct def) {
        if (amount > player.getInventory().getAmount(new Item(def.getItem()))) {
            amount = player.getInventory().getAmount(new Item(def.getItem()));
        }
        int coins = 0;
        if (def == SOFT_LEATHER) {
            coins = 1;
        } else if (def == HARD_LEATHER) {
            coins = 3;
        } else if (def == SNAKESKIN) {
            coins = 20;
        } else if (def == SNAKESKIN2) {
            coins = 15;
        } else {
            coins = 20;
        }
        if (amount == 0) {
            return;
        }
        if (!player.getInventory().contains(def.getItem(), amount)) {
            player.getPacketDispatch().sendMessage("You don't have any " + ItemDefinition.forId(def.getItem()).getName().toLowerCase() + " to tan.");
            return;
        }
        player.getInterfaceManager().close();
        if (!player.getInventory().contains(995, coins * amount)) {
            player.getPacketDispatch().sendMessage("You don't have enough coins to tan that many.");
            return;
        }
        if (player.getInventory().remove(new Item(995, coins * amount)) && player.getInventory().remove(new Item(def.getItem(), amount))) {
            player.getInventory().add(new Item(def.getProduct(), amount));
            if (amount > 1) {
                player.getPacketDispatch().sendMessage("The tanner tans " + amount + " " + ItemDefinition.forId(def.getItem()).getName().toLowerCase() + "s for you.");
            } else {
                player.getPacketDispatch().sendMessage("The tanner tans your " + ItemDefinition.forId(def.getItem()).getName().toLowerCase() + ".");
            }
            if (def == SOFT_LEATHER) {
                player.getAchievementDiaryManager().finishTask(player, DiaryType.LUMBRIDGE, 1, 2);
            }
        } else {
            player.getPacketDispatch().sendMessage("You don't have enough coins to tan that many.");
        }
    }
}
