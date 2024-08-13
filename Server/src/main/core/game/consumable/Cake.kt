package core.game.consumable

import content.data.consumables.Consumables.Companion.getConsumableById
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Instantiates a new Cake.
 *
 * @param ids      the ids
 * @param effect   the effect
 * @param messages the messages
 */
class Cake(
    ids: IntArray?,
    effect: ConsumableEffect?,
    vararg messages: String?
) : Food(ids, effect, *messages) {

    // Override the consume method to define the consumption behavior of the Cake
    override fun consume(item: Item, player: Player) {
        // Execute actions related to the consumption of the item
        executeConsumptionActions(player)

        // Get the next item ID after consumption
        val nextItemId = getNextItemId(item.id)

        // Check if there is a next item ID; if so, replace the current item in the inventory
        if (nextItemId != -1) {
            player.inventory.replace(Item(nextItemId), item.slot)
        } else {
            // If no next item ID, remove the item from the inventory
            player.inventory.remove(item)
        }

        // Store the player's initial life points before consumption
        val initialLifePoints = player.getSkills().lifepoints

        // Activate the consumable effect for the player
        getConsumableById(item.id)!!.consumable.effect.activate(player)

        // Send messages to the player regarding the consumption
        sendMessages(player, initialLifePoints, item, messages)
    }

    // Override the sendMessages method to handle message sending after consumption
    override fun sendMessages(player: Player, initialLifePoints: Int, item: Item, messages: Array<String>) {
        // Check if there are custom messages; if not, send default messages
        if (messages.size == 0) {
            sendDefaultMessages(player, item)
        } else {
            // Send custom messages based on the provided messages array
            sendCustomMessages(player, messages, item.id)
        }

        // Send a healing message to the player based on their initial life points
        sendHealingMessage(player, initialLifePoints)
    }

    // Private method to send custom messages to the player
    private fun sendCustomMessages(player: Player, messages: Array<String>, itemId: Int) {
        var i = 0
        // Loop through the ids to find the index of the current item ID
        while (ids[i] != itemId) {
            i++
        }
        // Send the corresponding message to the player
        sendMessage(player, messages[i])
    }
}
