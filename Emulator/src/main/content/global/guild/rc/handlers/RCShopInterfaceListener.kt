package content.global.guild.rc.handlers

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
    class ShopItem(val id: Int, val price: Int, val amount: Int)

    override fun defineInterfaceListeners() {
        onOpen(Components.RCGUILD_REWARDS_779) { player, _ ->
            sendTokens(player)
            return@onOpen true
        }

        on(Components.RCGUILD_REWARDS_779) { player, _, opcode, button, _, _ ->
            var choice: ShopItem
            when (button) {
                6 ->  choice = airTalisman
                13 -> choice = mindTalisman
                15 -> choice = waterTalisman
                10 -> choice = earthTalisman
                11 -> choice = fireTalisman
                7 ->  choice = bodyTalisman
                9 ->  choice = cosmicTalisman
                8 ->  choice = chaosTalisman
                14 -> choice = natureTalisman
                12 -> choice = lawTalisman
                36 -> choice = blueRCHat
                37 -> choice = yellowRCHat
                38 -> choice = greenRCHat
                39 -> choice = blueRCRobe
                40 -> choice = yellowRCRobe
                41 -> choice = greenRCRobe
                42 -> choice = blueRCBottom
                43 -> choice = yellowRCBottom
                44 -> choice = greenRCBottom
                45 -> choice = blueRCGloves
                46 -> choice = yellowRCGloves
                47 -> choice = greenRCGloves
                72 -> choice = airTablet
                80 -> choice = mindTablet
                83 -> choice = waterTablet
                77 -> choice = earthTablet
                78 -> choice = fireTablet
                73 -> choice = bodyTablet
                75 -> choice = cosmicTablet
                74 -> choice = chaosTablet
                81 -> choice = astralTablet
                82 -> choice = natureTablet
                79 -> choice = lawTablet
                76 -> choice = deathTablet
                84 -> choice = bloodTablet
                85 -> choice = guildTablet
                114-> choice = rcStaff
                115-> choice = pureEssence
                else -> log(this::class.java, Log.WARN, "Unhandled button ID for RC shop interface: $button").also { return@on true }
            }

            handleOpcode(choice, opcode, player)
            if (opcode == 155) {
                when (button) {
                    163 -> sendMessage(player, "You must select something to buy before you can confirm your purchase")
                }
            }
            sendItem(choice, choice.amount, player)
            return@on true
        }

    }

    private fun handleBuyOption(item: ShopItem, amount: Int, player: Player) {
        val neededTokens = Item(Items.RUNECRAFTING_GUILD_TOKEN_13650, item.price * amount)
        if (!player.inventory.containsItem(neededTokens)) {
            sendMessage(player, "You don't have enough tokens to purchase that.")
            return
        }
        if (freeSlots(player) == 0) {
            sendMessage(player, "You don't have enough space in your inventory.")
            return
        }

        sendMessage(player, "Your purchase has been added to your inventory.")
        player.inventory.remove(neededTokens)
        player.inventory.add(Item(item.id, amount))
        sendInterfaceText(player, " ", Components.RCGUILD_REWARDS_779, 136)
        sendTokens(player)
    }

    private fun handleOpcode(item: ShopItem, opcode: Int, player: Player) {
        when (opcode) {
            155 -> handleBuyOption(item, 1, player)
            196 -> {
                sendInputDialogue(player, InputType.AMOUNT, "Enter the amount to buy:") { value ->
                    val amt = value.toString().toIntOrNull()
                    if (amt == null || amt <= 0) {
                        sendDialogue(player, "Please enter a valid amount greater than zero.")
                        return@sendInputDialogue
                    } else {
                        handleBuyOption(item, amt, player)
                    }
                }
            }
        }
    }

    private fun sendTokens(player: Player) {
        sendInterfaceText(player, "Tokens: ${amountInInventory(player, 13650)}", Components.RCGUILD_REWARDS_779, 135)
    }

    private fun sendItem(item: ShopItem, amount: Int, player: Player) {
        sendInterfaceText(player, "${getItemName(item.id)}($amount)", Components.RCGUILD_REWARDS_779, 136)
    }

    private val airTalisman = ShopItem(1438, 50, 1)
    private val mindTalisman = ShopItem(1448, 50, 1)
    private val waterTalisman = ShopItem(1444, 50, 1)
    private val earthTalisman = ShopItem(1440, 50, 1)
    private val fireTalisman = ShopItem(1442, 50, 1)
    private val bodyTalisman = ShopItem(1446, 50, 1)
    private val cosmicTalisman = ShopItem(1454, 125, 1)
    private val chaosTalisman = ShopItem(1452, 125, 1)
    private val natureTalisman = ShopItem(1462, 125, 1)
    private val lawTalisman = ShopItem(1458, 125, 1)
    private val blueRCHat = ShopItem(13626, 1000, 1)
    private val yellowRCHat = ShopItem(13616, 1000, 1)
    private val greenRCHat = ShopItem(13621, 1000, 1)
    private val blueRCRobe = ShopItem(13624, 1000, 1)
    private val yellowRCRobe = ShopItem(13614, 1000, 1)
    private val greenRCRobe = ShopItem(13619, 1000, 1)
    private val blueRCBottom = ShopItem(13627, 1000, 1)
    private val yellowRCBottom = ShopItem(13617, 1000, 1)
    private val greenRCBottom = ShopItem(13622, 1000, 1)
    private val blueRCGloves = ShopItem(13628, 1000, 1)
    private val yellowRCGloves = ShopItem(13618, 1000, 1)
    private val greenRCGloves = ShopItem(13623, 1000, 1)
    private val rcStaff = ShopItem(13629, 10000, 1)
    private val pureEssence = ShopItem(7937, 100, 1)
    private val airTablet = ShopItem(13599, 30, 1)
    private val mindTablet = ShopItem(13600, 32, 1)
    private val waterTablet = ShopItem(13601, 34, 1)
    private val earthTablet = ShopItem(13602, 36, 1)
    private val fireTablet = ShopItem(13603, 37, 1)
    private val bodyTablet = ShopItem(13604, 38, 1)
    private val cosmicTablet = ShopItem(13605, 39, 1)
    private val chaosTablet = ShopItem(13606, 40, 1)
    private val astralTablet = ShopItem(13611, 41, 1)
    private val natureTablet = ShopItem(13607, 42, 1)
    private val lawTablet = ShopItem(13608, 43, 1)
    private val deathTablet = ShopItem(13609, 44, 1)
    private val bloodTablet = ShopItem(13610, 45, 1)
    private val guildTablet = ShopItem(13598, 15, 1)
}
