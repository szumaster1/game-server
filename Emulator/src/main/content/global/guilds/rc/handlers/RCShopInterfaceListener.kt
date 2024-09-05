package content.global.guilds.rc.handlers

import cfg.consts.Components
import cfg.consts.Items
import core.api.*
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.Log

/**
 * Represents the rc shop interface listener.
 */
class RCShopInterfaceListener : InterfaceListener {

    /**
     * Represents an item data for the rc shop.
     *
     * @param id the item's ID.
     * @param price the item's price.
     * @param amount the amount of the item.
     */
    data class ShopItem(val id: Int, val price: Int, val amount: Int)

    private val shopItems = mapOf(
        6 to ShopItem(1438, 50, 1),
        13 to ShopItem(1448, 50, 1),
        15 to ShopItem(1444, 50, 1),
        10 to ShopItem(1440, 50, 1),
        11 to ShopItem(1442, 50, 1),
        7 to ShopItem(1446, 50, 1),
        9 to ShopItem(1454, 125, 1),
        8 to ShopItem(1452, 125, 1),
        14 to ShopItem(1462, 125, 1),
        12 to ShopItem(1458, 125, 1),
        36 to ShopItem(13626, 1000, 1),
        37 to ShopItem(13616, 1000, 1),
        38 to ShopItem(13621, 1000, 1),
        39 to ShopItem(13624, 1000, 1),
        40 to ShopItem(13614, 1000, 1),
        41 to ShopItem(13619, 1000, 1),
        42 to ShopItem(13627, 1000, 1),
        43 to ShopItem(13617, 1000, 1),
        44 to ShopItem(13622, 1000, 1),
        45 to ShopItem(13628, 1000, 1),
        46 to ShopItem(13618, 1000, 1),
        47 to ShopItem(13623, 1000, 1),
        72 to ShopItem(13599, 30, 1),
        80 to ShopItem(13600, 32, 1),
        83 to ShopItem(13601, 34, 1),
        77 to ShopItem(13602, 36, 1),
        78 to ShopItem(13603, 37, 1),
        73 to ShopItem(13604, 38, 1),
        75 to ShopItem(13605, 39, 1),
        74 to ShopItem(13606, 40, 1),
        81 to ShopItem(13611, 41, 1),
        82 to ShopItem(13607, 42, 1),
        79 to ShopItem(13608, 43, 1),
        76 to ShopItem(13609, 44, 1),
        84 to ShopItem(13610, 45, 1),
        85 to ShopItem(13598, 15, 1),
        114 to ShopItem(13629, 10000, 1),
        115 to ShopItem(7937, 100, 1)
    )

    override fun defineInterfaceListeners() {
        onOpen(Components.RCGUILD_REWARDS_779) { player, _ ->
            setAttribute(player, "rcshop:purchase", 0)
            sendTokens(player)
            return@onOpen true
        }

        on(Components.RCGUILD_REWARDS_779) { player, _, opcode, button, _, _ ->
            val choice = shopItems[button] ?: run {
                log(this::class.java, Log.WARN, "Unhandled button ID for RC shop interface: $button")
                return@on true
            }
            if (button == 163) {
                if (getAttribute(player, "rcshop:purchase", -1) < 1000)
                    sendMessage(player, "You must select something to buy before you can confirm your purchase")
            } else {
                confirmPurchase(choice, opcode, player)
            }
            handleOpcode(choice, opcode, player)
            return@on true
        }
    }

    private fun sendBuyOption(item: ShopItem, amount: Int, player: Player) {
        getAttribute(player, "rcshop:purchase", setAttribute(player, "rcshop:item", item.id))
        setInterfaceText(player, getItemName(item.id) + "(${amount})", Components.RCGUILD_REWARDS_779, 136)
    }

    private fun confirmPurchase(item: ShopItem, amount: Int, player: Player) {
        var neededTokens = Item(Items.RUNECRAFTING_GUILD_TOKEN_13650, item.price * amount)
        if (!player.inventory.containsItem(neededTokens)) {
            sendMessage(player, "You don't have enough tokens to purchase that.")
            return
        }
        if (!hasSpaceFor(player, Item(item.id, amount))) {
            sendMessage(player, "You don't have enough space in your inventory.")
        }
        removeItem(player, neededTokens)
        addItem(player, item.id, amount)
        sendMessage(player, "Your purchase has been added to your inventory.")
        setInterfaceText(player, "", Components.RCGUILD_REWARDS_779, 136)
        sendTokens(player)
    }

    private fun handleOpcode(item: ShopItem, opcode: Int, player: Player) {
        when (opcode) {
            155 -> {
                sendBuyOption(item, 1, player)
            }

            196 -> {
                sendInputDialogue(player, InputType.AMOUNT, "Enter the amount to buy:") { value ->
                    val amt = value as Int
                    sendBuyOption(item, amt, player)
                }
            }
        }
    }

    private fun sendTokens(player: Player) {
        setInterfaceText(
            player,
            "Tokens: ${amountInInventory(player, Items.RUNECRAFTING_GUILD_TOKEN_13650)}",
            Components.RCGUILD_REWARDS_779,
            135
        )
    }
}
