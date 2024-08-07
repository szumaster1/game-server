package core.game.consumable

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Halfable food
 *
 * @constructor
 *
 * @param ids
 * @param effect
 * @param messages
 */
class HalfableFood
/**
 * Instantiates a new Half able food.
 *
 * @param ids      the ids
 * @param effect   the effect
 * @param messages the messages
 */
    (ids: IntArray?, effect: ConsumableEffect?, vararg messages: String?) : Food(ids, effect, *messages) {
    override fun sendDefaultMessages(player: Player, item: Item) {
        if (item.id == ids[0]) {
            sendMessage(player, "You eat half the " + getFormattedName(item) + ".")
        } else if (item.id == ids[1]) {
            sendMessage(player, "You eat the remaining " + getFormattedName(item) + ".")
        } else {
            super.sendDefaultMessages(player, item)
        }
    }

    override fun getFormattedName(item: Item): String {
        return item.name.replace("1/2", "").replace("Half an", "").replace("Half a", "").trim { it <= ' ' }.lowercase()
    }
}
