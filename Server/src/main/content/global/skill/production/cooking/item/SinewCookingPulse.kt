package content.global.skill.production.cooking.item

import content.global.skill.production.cooking.CookableItems
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

class SinewCookingPulse(player: Player?, `object`: Scenery?, initial: Int, product: Int, amount: Int) :
    StandardCookingPulse(player!!, `object`!!, initial, product, amount) {
    override fun checkRequirements(): Boolean {
        properties = CookableItems.SINEW
        return super.checkRequirements()
    }

    override fun isBurned(player: Player, `object`: Scenery, food: Int): Boolean {
        return false
    }

    override fun getMessage(food: Item, product: Item, burned: Boolean): String {
        return "You dry a piece of beef and extract the sinew."
    }
}
