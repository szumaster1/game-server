package core.game.consumable

import content.data.consumables.Consumables.Companion.getConsumableById
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Cake
 *
 * @constructor
 *
 * @param ids
 * @param effect
 * @param messages
 */
class Cake(
    /**
     * Instantiates a new Cake.
     *
     * @param ids      the ids
     * @param effect   the effect
     * @param messages the messages
     */
    ids: IntArray?,
    effect: ConsumableEffect?,
    vararg messages: String?
) : Food(ids, effect, *messages) {

    override fun consume(item: Item, player: Player) {
        executeConsumptionActions(player)
        val nextItemId = getNextItemId(item.id)
        if (nextItemId != -1) {
            player.inventory.replace(Item(nextItemId), item.slot)
        } else {
            player.inventory.remove(item)
        }
        val initialLifePoints = player.getSkills().lifepoints
        getConsumableById(item.id)!!.consumable.effect.activate(player)
        sendMessages(player, initialLifePoints, item, messages)
    }

    override fun sendMessages(player: Player, initialLifePoints: Int, item: Item, messages: Array<String>) {
        if (messages.size == 0) {
            sendDefaultMessages(player, item)
        } else {
            sendCustomMessages(player, messages, item.id)
        }
        sendHealingMessage(player, initialLifePoints)
    }

    private fun sendCustomMessages(player: Player, messages: Array<String>, itemId: Int) {
        var i = 0
        while (ids[i] != itemId) {
            i++
        }
        sendMessage(player, messages[i])
    }
}
