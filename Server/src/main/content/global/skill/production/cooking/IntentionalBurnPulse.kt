package content.global.skill.production.cooking

import content.global.skill.production.cooking.item.StandardCookingPulse
import core.api.addItem
import core.api.consts.Sounds
import core.api.playAudio
import core.api.removeItem
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Intentional burn pulse
 *
 * @param player Represents the player involved in the burn pulse action.
 * @param scenery Represents the environment or setting where the burn pulse occurs.
 * @param initial Represents the initial state or value before the burn pulse.
 * @param product Represents the product that is being affected by the burn pulse.
 * @param amount Represents the quantity of the product involved in the burn pulse.
 * @constructor Intentional burn pulse initializes the properties for the burn pulse action.
 */
class IntentionalBurnPulse
internal constructor(
    override var player: Player, // The player executing the burn pulse action
    override var scenery: Scenery, // The scenery where the action takes place
    override var initial: Int, // The initial state before the burn pulse
    override var product: Int, // The product being affected by the burn pulse
    override var amount: Int // The amount of product involved in the burn pulse
) : StandardCookingPulse(player, scenery, initial, product, amount) {

    override fun checkRequirements(): Boolean {
        return scenery.isActive
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            delay = if (scenery.name.equals("range", ignoreCase = true)) 5 else 4
            return false
        }
        if (cook(player, null, false, initial, product)) {
            amount--
        } else {
            return true
        }
        /*
         * we are always one off a normal cooking pulse because
         * the first tick is handled outside of this class.
         */
        return amount <= 1
    }

    override fun cook(player: Player, sceneryId: Scenery?, burned: Boolean, initial: Int, product: Int): Boolean {
        super.animate()
        val initialItem = Item(initial)
        val productItem = Item(product)

        if (removeItem(player, initialItem)) {
            addItem(player, productItem.id)
            getMessage(initialItem, productItem, burned)?.let { sendMessage(player, it) }
            playAudio(player, Sounds.FRY_2577)
            return true
        }
        return false
    }
}
