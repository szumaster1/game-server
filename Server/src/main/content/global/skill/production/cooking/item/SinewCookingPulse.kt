package content.global.skill.production.cooking.item

import content.global.skill.production.cooking.CookableItems
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Sinew cooking pulse.
 * @param scenery Represents the cooking scenery or environment.
 * @param initial The initial state or value for the cooking process.
 * @param product The product being cooked, represented as an integer.
 * @param amount The quantity of the product to be cooked.
 */
class SinewCookingPulse(
    player: Player?, // The player who is performing the cooking action, can be null.
    scenery: Scenery?, // The scenery where the cooking takes place, can be null.
    initial: Int, // The starting point for the cooking process.
    product: Int, // The identifier for the product being cooked.
    amount: Int // The total amount of the product to be cooked.
) : StandardCookingPulse(player!!, scenery!!, initial, product, amount) {

    override fun checkRequirements(): Boolean {
        properties = CookableItems.SINEW
        return super.checkRequirements()
    }

    override fun isBurned(player: Player, scenery: Scenery, food: Int): Boolean {
        return false
    }

    override fun getMessage(food: Item, product: Item, burned: Boolean): String {
        return "You dry a piece of beef and extract the sinew."
    }
}
