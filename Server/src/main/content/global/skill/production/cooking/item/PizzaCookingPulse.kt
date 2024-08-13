package content.global.skill.production.cooking.item

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Pizza cooking pulse
 *
 * @param player The player who is cooking the pizza
 * @param scenery The environment in which the pizza is being cooked
 * @constructor
 *
 * @param initial The initial state of the cooking process
 * @param product The type of pizza product being cooked
 * @param amount The quantity of pizza being cooked
 */
class PizzaCookingPulse(
    override var player: Player, // The player instance involved in the cooking process
    override var scenery: Scenery, // The scenery instance representing the cooking environment
    initial: Int, // The initial cooking state passed to the superclass
    product: Int, // The specific pizza product identifier
    amount: Int // The amount of pizza to be cooked
) : StandardCookingPulse(player, scenery, initial, product, amount) {

    override fun checkRequirements(): Boolean {
        if (!scenery.name.lowercase().contains("range")) {
            sendMessage(player, "This can only be cooked on a range.")
            return false
        }
        return super.checkRequirements()
    }

    override fun getMessage(food: Item, product: Item, burned: Boolean): String {
        return if (burned) {
            "You accidentally burn the pizza."
        } else {
            "You cook a delicious looking pizza."
        }
    }
}
