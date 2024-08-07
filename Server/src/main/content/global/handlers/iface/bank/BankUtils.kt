package content.global.handlers.iface.bank

import core.api.InputType
import core.api.sendInputDialogue
import core.game.node.entity.player.Player

/**
 * Bank utils.
 */
object BankUtils {
    /**
     * Transfer a specified amount of items from the bank.
     *
     * @param player The player performing the transfer.
     * @param slot The slot of the item in the bank.
     * @param withdraw True if withdrawing, False if depositing.
     * @param after Callback function to execute after the transfer.
     */
    fun transferX(player: Player, slot: Int, withdraw: Boolean, after: (() -> Unit)? = null) {
        // Prompt the player to enter the amount
        sendInputDialogue(player, InputType.AMOUNT, "Enter the amount:") { value ->
            val number = Integer.parseInt(value.toString())

            // Perform withdrawal or deposit based on the 'withdraw' parameter
            if (withdraw) {
                player.bank.takeItem(slot, number) // Withdraw item from the bank
            } else {
                player.bank.addItem(slot, number) // Deposit item into the bank
            }

            player.bank.updateLastAmountX(number) // Update the last amount used
            after?.let { it() } // Execute the callback function if provided
        }
    }
}
