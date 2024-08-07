package core.game.consumable

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Fake consumable
 *
 * @constructor
 *
 * @param id
 * @param messages
 */
class FakeConsumable
/**
 * Instantiates a new Fake consumable.
 *
 * @param id       the id
 * @param messages the messages
 */
    (id: Int, messages: Array<String?>) : Consumable(intArrayOf(id), null, *messages) {
    override fun consume(item: Item, player: Player) {
        sendDefaultMessages(player, item)
    }

    override fun sendDefaultMessages(player: Player, item: Item) {
        for (message in messages) {
            sendMessage(player, message)
        }
    }

    override fun executeConsumptionActions(player: Player) {
    }
}
