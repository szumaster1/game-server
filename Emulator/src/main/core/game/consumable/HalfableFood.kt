package core.game.consumable

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents any food that is consumed in two parts such as pies and pizzas.
 *
 * @param ids      the ids
 * @param effect   the effect
 * @param messages the messages
*/
class HalfableFood(ids: IntArray?, effect: ConsumableEffect?, vararg messages: String?) : Food(ids, effect, *messages) {
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
