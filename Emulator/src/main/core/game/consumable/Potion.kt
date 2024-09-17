package core.game.consumable

import content.data.consumables.Consumables.Companion.getConsumableById
import org.rs.consts.Items
import org.rs.consts.Sounds
import core.api.playAudio
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Potion.
 */
open class Potion
/**
 * Instantiates a new Potion.
 *
 * @param ids      the ids
 * @param effect   the effect
 * @param messages the messages
 */
    (ids: IntArray?, effect: ConsumableEffect?, vararg messages: String?) : Drink(ids, effect, *messages) {
    override fun consume(item: Item, player: Player) {
        executeConsumptionActions(player)
        val nextItemId = getNextItemId(item.id)
        if (nextItemId != -1) {
            player.inventory.replace(Item(nextItemId), item.slot)
        } else {
            player.inventory.replace(Item(vial), item.slot)
        }

        val initialLifePoints = player.getSkills().lifepoints
        getConsumableById(item.id)!!.consumable.effect.activate(player)
        if (messages.size == 0) {
            sendDefaultMessages(player, item)
        } else {
            sendCustomMessages(player, messages)
        }
        sendHealingMessage(player, initialLifePoints)
    }

    override fun executeConsumptionActions(player: Player) {
        player.animate(animation)
        playAudio(player, sound)
    }

    override fun sendDefaultMessages(player: Player, item: Item) {
        var consumedDoses = 1
        var i = 0
        while (ids[i] != item.id) {
            consumedDoses++
            i++
        }
        val dosesLeft = ids.size - consumedDoses
        sendMessage(player, "You drink some of your " + getFormattedName(item) + ".")
        if (dosesLeft > 1) {
            sendMessage(player, "You have $dosesLeft doses of potion left.")
        } else if (dosesLeft == 1) {
            sendMessage(player, "You have 1 dose of potion left.")
        } else {
            sendMessage(player, "You have finished your potion.")
        }
    }

    /**
     * Get dose int.
     *
     * @param potion the potion
     * @return the int
     */
    fun getDose(potion: Item): Int {
        for (i in ids.indices) if (ids[i] == potion.id) return ids.size - i
        return potion.name.replace("[^\\d.]".toRegex(), "").toInt()
    }

    override fun getIds(): IntArray {
        return ids
    }

    override fun getEffect(): ConsumableEffect {
        return effect
    }

    companion object {
        private const val vial = Items.VIAL_229
        private const val sound = Sounds.LIQUID_2401
    }
}
