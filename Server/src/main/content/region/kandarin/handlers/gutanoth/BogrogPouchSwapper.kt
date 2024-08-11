package content.region.kandarin.handlers.gutanoth

import content.global.skill.combat.summoning.SummoningPouch
import content.global.skill.combat.summoning.SummoningScroll
import core.api.InputType
import core.api.sendInputDialogue
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.zone.ZoneBorders
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Bogrog pouch swapper.
 */
object BogrogPouchSwapper {

    private const val OP_VALUE = 0
    private const val OP_SWAP_1 = 1
    private const val OP_SWAP_5 = 2
    private const val OP_SWAP_10 = 3
    private const val OP_SWAP_X = 4
    private const val SPIRIT_SHARD = 12183

    private val GEBorders = ZoneBorders(3151, 3501, 3175, 3477)

    /**
     * This function handles the player's interaction with a specific option in a slot.
     *
     * @param player The player interacting with the option.
     * @param optionIndex The index of the option selected.
     * @param slot The slot where the option is located.
     * @return Boolean value indicating the success of handling the interaction.
     */
    @JvmStatic
    fun handle(player: Player, optionIndex: Int, slot: Int): Boolean {
        val item = player.inventory.get(slot) ?: return false
        return when (optionIndex) {
            OP_VALUE -> sendValue(item.id, player)
            OP_SWAP_1 -> swap(player, 1, item.id)
            OP_SWAP_5 -> swap(player, 5, item.id)
            OP_SWAP_10 -> swap(player, 10, item.id)
            OP_SWAP_X -> true.also {
                sendInputDialogue(player, InputType.AMOUNT, "Enter the amount:") { value ->
                    swap(player, value as Int, item.id)
                }
            }

            else -> false
        }
    }

    /**
     * Swap function to exchange items between players.
     *
     * @param player the player initiating the swap.
     * @param amount the quantity of items to swap.
     * @param itemID the unique identifier of the item to be swapped.
     * @return true if the swap was successful, false otherwise.
     */
    private fun swap(player: Player, amount: Int, itemID: Int): Boolean {
        var amt = amount
        val value = getValue(itemID)
        if (value == 0.0) {
            return false
        }
        val inInventory = player.inventory.getAmount(itemID)
        if (amount > inInventory)
            amt = inInventory
        player.inventory.remove(Item(itemID, amt))
        player.inventory.add(Item(SPIRIT_SHARD, floor(value * amt).toInt()))
        return true
    }

    /**
     * Sends the value of an item to a player.
     *
     * @param itemID the ID of the item to send.
     * @param player the player to send the item value to.
     * @return true if the value was successfully sent, false otherwise.
     */
    private fun sendValue(itemID: Int, player: Player): Boolean {
        val value = getValue(itemID)
        if (value == 0.0) {
            return false
        }

        sendMessage(player, "Bogrog will give you ${floor(value).toInt()} shards for that.")
        return true
    }

    /**
     * This function retrieves the value associated with a specific item ID.
     *
     * @param itemID The ID of the item for which the value is requested.
     * @return The value associated with the provided item ID.
     */
    private fun getValue(itemID: Int): Double {
        var item = SummoningPouch.get(itemID)
        var isScroll = false
        if (item == null) item = SummoningPouch.get(Item(itemID).noteChange)
        if (item == null) item = SummoningPouch.get(
            SummoningScroll.forItemId(itemID)?.pouch ?: -1
        ).also { isScroll = true }
        item ?: return 0.0
        var shardQuantity = item.items[item.items.size - 1].amount * 0.7
        return if (isScroll) shardQuantity / 20.0 else ceil(shardQuantity)
    }
}