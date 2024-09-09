package core.game.consumable

import content.data.consumables.Consumables.Companion.getConsumableById
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents any cake item that is made of three even slices.
 *
 * @param ids      the ids
 * @param effect   the effect
 * @param messages the messages
 */
class Cake(
    private val ids: IntArray,
    effect: ConsumableEffect?,
    vararg messages: String?
) : Food(ids, effect, *messages) {

    /**
     * Override the consume method to define the consumption behavior of the Cake.
     */
    override fun consume(item: Item, player: Player) {
        executeConsumptionActions(player)

        val nextItemId = getNextItemId(item.id)
        if (nextItemId != -1) {
            player.inventory.replace(Item(nextItemId), item.slot)
        } else {
            player.inventory.remove(item)
        }

        val initialLifePoints = player.getSkills().lifepoints
        getConsumableById(item.id)?.consumable?.effect?.activate(player)
        sendMessages(player, initialLifePoints, item, messages)
    }

    /**
     * Override the sendMessages method to handle message sending after consumption.
     */
    override fun sendMessages(player: Player, initialLifePoints: Int, item: Item, messages: Array<out String?>) {
        if (messages.isEmpty()) {
            sendDefaultMessages(player, item)
        } else {
            sendCustomMessages(player, messages, item.id)
        }
        sendHealingMessage(player, initialLifePoints)
    }

    /**
     * Private method to send custom messages to the player.
     */
    private fun sendCustomMessages(player: Player, messages: Array<out String?>, itemId: Int) {
        val index = ids.indexOf(itemId)
        if (index != -1) {
            sendMessage(player, messages[index] ?: "")
        }
    }
}
