package core.game.consumable

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents a Barbarian mix consumable item.
 *
 * @param ids           an array of item IDs associated with the Barbarian mix.
 * @param effect        the effect that the Barbarian mix will have when consumed.
 * @param messages      vararg parameter for any additional messages related to the Barbarian mix.
 */
class BarbarianMix(
    ids: IntArray? = null,
    effect: ConsumableEffect? = null,
    vararg messages: String?
) : Potion(ids, effect, *messages) {

    /**
     * Sends a custom message to the player when the potion is consumed.
     *
     * @param player    the player consuming the potion.
     * @param item      the item being consumed.
     */
    override fun sendDefaultMessages(player: Player, item: Item) {
        sendMessage(player, "You drink the lumpy potion.")
    }
}