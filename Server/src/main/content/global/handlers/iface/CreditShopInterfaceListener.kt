package content.global.handlers.iface

import cfg.consts.Components
import cfg.consts.Items
import core.api.sendDialogue
import core.api.sendMessage
import core.api.setInterfaceText
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Credit shop interface listener.
 */
class CreditShopInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.CREDIT_SHOP_837) { player, _, opcode, buttonID, _, _ ->
            val item = getItem(buttonID)

            if (opcode == 155) {
                sendMessage(player, "This item costs ${item.price} credits.")
                return@on true
            }

            if (buttonID == 14 || buttonID == 21) {
                val specific = when (opcode) {
                    196 -> if (buttonID == 14) Items.RED_PARTYHAT_1038 else Items.RED_HWEEN_MASK_1057
                    124 -> if (buttonID == 14) Items.GREEN_PARTYHAT_1044 else Items.GREEN_HWEEN_MASK_1053
                    199 -> if (buttonID == 14) Items.BLUE_PARTYHAT_1042 else Items.BLUE_HWEEN_MASK_1055
                    234 -> Items.YELLOW_PARTYHAT_1040
                    168 -> Items.PURPLE_PARTYHAT_1046
                    166 -> Items.WHITE_PARTYHAT_1048
                    else -> Items.DWARF_WEED_SEED_5303
                }
                attemptPurchase(player, specific, item.price)
            } else {
                attemptPurchase(player, item.id, item.price)
            }
            return@on true
        }

        onOpen(Components.CREDIT_SHOP_837) { player, _ ->
            sendCredits(player)
            return@onOpen true
        }
    }

    private fun getItem(buttonID: Int): ShopItem {
        return when (buttonID) {
            14 -> ShopItem(Items.BLUE_PARTYHAT_1042, 75)
            18 -> ShopItem(Items.SCYTHE_1419, 100)
            20 -> ShopItem(Items.JANGLES_THE_MONKEY_14648, 200)
            17 -> ShopItem(Items.CHRISTMAS_CRACKER_962, 65)
            21 -> ShopItem(Items.BLUE_HWEEN_MASK_1055, 65)
            16 -> ShopItem(Items.SANTA_HAT_1050, 65)
            19 -> ShopItem(Items.BUNNY_EARS_1037, 150)
            15 -> ShopItem(Items.EASTER_RING_7927, 100)
            else -> ShopItem(0, 0)
        }
    }

    /**
     * Send credits
     *
     * @param player The player to whom credits are being sent.
     */
    fun sendCredits(player: Player) {
        // Set the interface text to display the player's current credits
        setInterfaceText(player, "You have ${player.details.credits} credits to spend.", Components.CREDIT_SHOP_837, 39)
    }

    /**
     * Attempt purchase
     *
     * @param player The player attempting to make a purchase.
     * @param item The ID of the item being purchased.
     * @param price The price of the item.
     */
    fun attemptPurchase(player: Player, item: Int, price: Int) {
        // Check if the player has enough credits to make the purchase
        if (player.details.credits < price) {
            // Inform the player they do not have enough credits
            sendDialogue(player, "You don't have enough credits for that.")
            return // Exit the function if credits are insufficient
        }

        // Attempt to add the item to the player's inventory
        if (player.inventory.add(Item(item))) {
            // Deduct the price from the player's credits if the item was added successfully
            player.details.credits -= price
        } else {
            // Inform the player they do not have enough inventory space
            sendDialogue(player, "You don't have enough inventory space for that.")
        }
        // Update the player on their remaining credits
        sendCredits(player)
    }

    /**
     * Shop item
     *
     * @param id The ID of the shop item.
     * @param price The price of the shop item.
     * @constructor Shop item
     */
    internal class ShopItem(val id: Int, val price: Int)
}
