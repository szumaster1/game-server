package core.game.consumable

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Barbarian mix
 *
 * @param ids An array of item IDs associated with the Barbarian mix.
 * @param effect The effect that the Barbarian mix will have when consumed.
 * @param messages Vararg parameter for any additional messages related to the Barbarian mix.
 */
class BarbarianMix(ids: IntArray?, effect: ConsumableEffect?, vararg messages: String?) :
    Potion(ids, effect, *messages) { // Inherits from Potion class with specified parameters

    // Overrides the sendDefaultMessages function to provide a custom message when the potion is consumed
    override fun sendDefaultMessages(player: Player, item: Item) {
        // Sends a message to the player indicating that they have consumed the potion
        sendMessage(player, "You drink the lumpy potion.")
    }
}
