package core.game.consumable

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents a special type of consumable that cannot be consumed unless it is transformed into another item.
 * During its 'consumption', only a message is sent to the player.
 */
class FakeConsumable(
    id: Int,
    messages: Array<String?>
) : Consumable(intArrayOf(id), null, *messages) {

    override fun consume(item: Item, player: Player) {
        sendDefaultMessages(player, item)
    }

    override fun sendDefaultMessages(player: Player, item: Item) {
        messages.forEach { message ->
            sendMessage(player, message)
        }
    }

    override fun executeConsumptionActions(player: Player) { }
}
