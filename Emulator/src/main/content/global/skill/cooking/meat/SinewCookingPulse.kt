package content.global.skill.cooking.meat

import content.global.skill.cooking.Cookable
import content.global.skill.cooking.StandardCookingPulse
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Represents the sinew cooking pulse.
 */
class SinewCookingPulse(player: Player?, scenery: Scenery?, initial: Int, product: Int, amount: Int) :
    StandardCookingPulse(player!!, scenery!!, initial, product, amount) {

    override fun checkRequirements(): Boolean {
        properties = Cookable.SINEW
        return super.checkRequirements()
    }

    override fun isBurned(player: Player, scenery: Scenery, food: Int): Boolean {
        return false
    }

    override fun getMessage(food: Item, product: Item, burned: Boolean): String {
        return "You dry a piece of beef and extract the sinew."
    }
}
