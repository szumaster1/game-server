package core.game.consumable

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Barbarian mix
 *
 * @constructor
 *
 * @param ids
 * @param effect
 * @param messages
 */
class BarbarianMix(ids: IntArray?, effect: ConsumableEffect?, vararg messages: String?) :
    Potion(ids, effect, *messages) {

    override fun sendDefaultMessages(player: Player, item: Item) {
        sendMessage(player, "You drink the lumpy potion.")
    }
}
