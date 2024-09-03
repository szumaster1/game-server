package content.global.skill.production.crafting.data

import cfg.consts.Components
import cfg.consts.Items
import core.api.*
import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item

/**
 * Represents the tanning items.
 */
enum class Tanning(
    val button: Int,
    val item: Int,
    val product: Int
) {
    /**
     * The soft leather.
     */
    SOFT_LEATHER(
        button = 1,
        item = Items.COWHIDE_1739,
        product = Items.LEATHER_1741
    ),

    /**
     * The hard leather.
     */
    HARD_LEATHER(
        button = 2,
        item = Items.COWHIDE_1739,
        product = Items.HARD_LEATHER_1743
    ),

    /**
     * The snakeskin.
     */
    SNAKESKIN(
        button = 3,
        item = Items.SNAKE_HIDE_6287,
        product = Items.SNAKESKIN_6289
    ),

    /**
     * The snakeskin (2).
     */
    SNAKESKIN2(
        button = 4,
        item = Items.SNAKE_HIDE_7801,
        product = Items.SNAKESKIN_6289
    ),

    /**
     * The green d'hide leather.
     */
    GREEN_DHIDE(
        button = 5,
        item = Items.GREEN_DRAGONHIDE_1753,
        product = Items.GREEN_D_LEATHER_1745
    ),

    /**
     * The blue d'hide leather.
     */
    BLUEDHIDE(
        button = 6,
        item = Items.BLUE_DRAGONHIDE_1751,
        product = Items.BLUE_D_LEATHER_2505
    ),

    /**
     * The red d'hide leather.
     */
    REDDHIDE(
        button = 7,
        item = Items.RED_DRAGONHIDE_1749,
        product = Items.RED_DRAGON_LEATHER_2507
    ),

    /**
     * The black d'hide leather.
     */
    BLACKDHIDE(
        button = 8,
        item = Items.BLACK_DRAGONHIDE_1747,
        product = Items.BLACK_D_LEATHER_2509
    );


    companion object {
        @JvmStatic
        fun forId(id: Int): Tanning? {
            for (def in values()) {
                if (def.button == id) {
                    return def
                }
            }
            return null
        }

        @JvmStatic
        fun forItemId(id: Int): Tanning? {
            for (def in values()) {
                if (def.item == id) {
                    return def
                }
            }
            return null
        }

        @JvmStatic
        fun open(player: Player, npc: Int) {
            player.interfaceManager.open(Component(Components.TANNER_324))
        }

        @JvmStatic
        fun tan(player: Player, amount: Int, def: Tanning) {
            var amount = amount
            if (amount > player.inventory.getAmount(Item(def.item))) {
                amount = player.inventory.getAmount(Item(def.item))
            }
            var coins = 0
            coins = if (def == SOFT_LEATHER) {
                1
            } else if (def == HARD_LEATHER) {
                3
            } else if (def == SNAKESKIN) {
                20
            } else if (def == SNAKESKIN2) {
                15
            } else {
                20
            }
            if (amount == 0) {
                return
            }
            if (!inInventory(player, def.item, amount)) {
                sendMessage(player, "You don't have any " + getItemName(def.item).lowercase() + " to tan.")
                return
            }
            player.interfaceManager.close()
            if (!inInventory(player, Items.COINS_995, coins * amount)) {
                sendMessage(player, "You don't have enough coins to tan that many.")
                return
            }
            if (removeItem(player, Item(Items.COINS_995, coins * amount)) && removeItem(
                    player,
                    Item(def.item, amount)
                )
            ) {
                addItem(player, def.product, amount)
                if (amount > 1) {
                    sendMessage(player, "The tanner tans " + amount + " " + getItemName(def.item).lowercase() + "s for you.")
                } else {
                    sendMessage(player, "The tanner tans your " + getItemName(def.item).lowercase() + ".")
                }
                if (def == SOFT_LEATHER) {
                    finishDiaryTask(player, DiaryType.LUMBRIDGE, 1, 2)
                }
            } else {
                sendMessage(player, "You don't have enough coins to tan that many.")
            }
        }
    }
}
