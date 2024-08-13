package content.global.skill.production.cooking.item

import core.api.getItemName
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Pie cooking pulse
 *
 * @param player The player who is cooking the pie
 * @param scenery The scenery where the cooking takes place
 * @constructor
 *
 * @param initial The initial state of the cooking process
 * @param product The type of product being cooked (in this case, a pie)
 * @param amount The amount of product being cooked
 */
class PieCookingPulse(
    override val player: Player, // The player involved in the cooking process
    override val scenery: Scenery, // The environment where the cooking occurs
    initial: Int, // The starting point of the cooking process
    product: Int, // The specific product identifier for the pie
    amount: Int // The quantity of pies being cooked
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
            "You accidentally burn the pie."
        } else {
            "You successfully bake a delicious " + getItemName(product.id).lowercase() + "."
        }
    }
}
