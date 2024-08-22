package content.region.misthalin.handlers.runecrafting

import core.api.*
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.Log
import cfg.consts.Components
import cfg.consts.Items

/**
 * Represents the Runecraft shop interface listener.
 * @author Szumaster
 */
class RunecraftShopInterfaceListener : InterfaceListener {

    /**
     * Runecrafting shop (tokens are purchased for coins).
     *
     * @param id the id.
     * @param price the price.
     * @param amount the amount.
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
                6 -> choice = Air_Talisman
                13 -> choice = Mind_Talisman
                15 -> choice = Water_Talisman
                10 -> choice = Earth_Talisman
                11 -> choice = Fire_Talisman
                7 -> choice = Body_Talisman
                9 -> choice = Cosmic_Talisman
                8 -> choice = Chaos_Talisman
                14 -> choice = Nature_Talisman
                12 -> choice = Law_Talisman
                36 -> choice = Blue_Runecrafting_Hat
                37 -> choice = Yellow_Runecrafting_Hat
                38 -> choice = Green_Runecrafting_Hat
                39 -> choice = Blue_Runecrafting_Robe
                40 -> choice = Yellow_Runecrafting_Robe
                41 -> choice = Green_Runecrafting_Robe
                42 -> choice = Blue_Runecrafting_Robe_Bottom
                43 -> choice = Yellow_Runecrafting_Robe_Bottom
                44 -> choice = Green_Runecrafting_Robe_Bottom
                45 -> choice = Blue_Runecrafting_Gloves
                46 -> choice = Yellow_Runecrafting_Gloves
                47 -> choice = Green_Runecrafting_Gloves
                72 -> choice = Air_Altar_Teleport
                80 -> choice = Mind_Altar_Teleport
                83 -> choice = Water_Altar_Teleport
                77 -> choice = Earth_Altar_Teleport
                78 -> choice = Fire_Altar_Teleport
                73 -> choice = Body_Altar_Teleport
                75 -> choice = Cosmic_Altar_Teleport
                74 -> choice = Chaos_Altar_Teleport
                81 -> choice = Astral_Altar_Teleport
                82 -> choice = Nature_Altar_Teleport
                79 -> choice = Law_Altar_Teleport
                76 -> choice = Death_Altar_Teleport
                84 -> choice = Blood_Altar_Teleport
                85 -> choice = Runecrafting_Guild_Teleport
                114 -> choice = Runecrafting_Staff
                115 -> choice = Pure_Essences
                else -> log(this::class.java, Log.WARN, "Unhandled button ID for RC shop interface: $button").also { return@on true }
            }
            handleOpcode(choice, opcode, player)
            if(opcode == 155){
                when(button) {
                    163 -> sendMessage(player, "You must select something to buy before you can confirm your purchase")
                }
            }
            return@on true
        }

    }

    private fun handleBuyOption(item: ShopItem, amount: Int, player: Player) {
        val neededTokens = Item(Items.RUNECRAFTING_GUILD_TOKEN_13650, item.price * amount)
        if (player.inventory.containsItem(neededTokens)) {
            if (player.inventory.hasSpaceFor(Item(item.id, amount))) {
                player.inventory.remove(neededTokens)
                player.inventory.add(Item(item.id, amount))
                sendTokens(player)
                sendMessage(player, "Your purchase has been added to your inventory.")
            } else {
                sendMessage(player, "You don't have enough space in your inventory.")
            }
        }
    }

    private fun handleOpcode(item: ShopItem, opcode: Int, player: Player) {
        when (opcode) {
            155 -> handleBuyOption(item, 1, player)
            196 -> {
                sendInputDialogue(player, InputType.AMOUNT, "Enter the amount to buy:") { value ->
                    val amt = value as Int
                    handleBuyOption(item, amt, player)
                }
            }
        }
    }

    private fun sendTokens(player : Player){
        setInterfaceText(player, "Tokens: ${amountInInventory(player, 13650)}", Components.RCGUILD_REWARDS_779, 135)
    }

    val Air_Talisman = (ShopItem(1438, 50, 1))
    val Mind_Talisman = (ShopItem(1448, 50, 1))
    val Water_Talisman = (ShopItem(1444, 50, 1))
    val Earth_Talisman = (ShopItem(1440, 50, 1))
    val Fire_Talisman = (ShopItem(1442, 50, 1))
    val Body_Talisman = (ShopItem(1446, 50, 1))
    val Cosmic_Talisman = (ShopItem(1454, 125, 1))
    val Chaos_Talisman = (ShopItem(1452, 125, 1))
    val Nature_Talisman = (ShopItem(1462, 125, 1))
    val Law_Talisman = (ShopItem(1458, 125, 1))
    val Blue_Runecrafting_Hat = (ShopItem(13626, 1000, 1))
    val Yellow_Runecrafting_Hat = (ShopItem(13616, 1000, 1))
    val Green_Runecrafting_Hat = (ShopItem(13621, 1000, 1))
    val Blue_Runecrafting_Robe = (ShopItem(13624, 1000, 1))
    val Yellow_Runecrafting_Robe = (ShopItem(13614, 1000, 1))
    val Green_Runecrafting_Robe = (ShopItem(13619, 1000, 1))
    val Blue_Runecrafting_Robe_Bottom = (ShopItem(13627, 1000, 1))
    val Yellow_Runecrafting_Robe_Bottom = (ShopItem(13617, 1000, 1))
    val Green_Runecrafting_Robe_Bottom = (ShopItem(13622, 1000, 1))
    val Blue_Runecrafting_Gloves = (ShopItem(13628, 1000, 1))
    val Yellow_Runecrafting_Gloves = (ShopItem(13618, 1000, 1))
    val Green_Runecrafting_Gloves = (ShopItem(13623, 1000, 1))
    val Runecrafting_Staff = (ShopItem(13629, 10000, 1))
    val Pure_Essences = (ShopItem(7937, 100, 1))
    val Air_Altar_Teleport = (ShopItem(13599, 30, 1))
    val Mind_Altar_Teleport = (ShopItem(13600, 32, 1))
    val Water_Altar_Teleport = (ShopItem(13601, 34, 1))
    val Earth_Altar_Teleport = (ShopItem(13602, 36, 1))
    val Fire_Altar_Teleport = (ShopItem(13603, 37, 1))
    val Body_Altar_Teleport = (ShopItem(13604, 38, 1))
    val Cosmic_Altar_Teleport = (ShopItem(13605, 39, 1))
    val Chaos_Altar_Teleport = (ShopItem(13606, 40, 1))
    val Astral_Altar_Teleport = (ShopItem(13611, 41, 1))
    val Nature_Altar_Teleport = (ShopItem(13607, 42, 1))
    val Law_Altar_Teleport = (ShopItem(13608, 43, 1))
    val Death_Altar_Teleport = (ShopItem(13609, 44, 1))
    val Blood_Altar_Teleport = (ShopItem(13610, 45, 1))
    val Runecrafting_Guild_Teleport = (ShopItem(13598, 15, 1))
}
