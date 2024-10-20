package content.global.skill.cooking.pizza

import content.global.skill.cooking.StandardCookingPulse
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Represents the pizza cooking pulse.
 */
class PizzaCookingPulse(
    override var player: Player,
    override var scenery: Scenery,
    initial: Int,
    product: Int,
    amount: Int
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
