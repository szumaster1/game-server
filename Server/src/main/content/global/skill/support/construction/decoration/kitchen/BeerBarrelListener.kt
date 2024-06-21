package content.global.skill.support.construction.decoration.kitchen

import core.api.addItem
import core.api.animate
import core.api.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.world.update.flag.context.Animation

class BeerBarrelListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, BARREL, BEER_GLASS){ player, used, _ ->
            val node = used.id as Scenery
            if (removeItem(player, Item(BEER_GLASS))) {
                animate(player, Animation.create(3661 + (used.id - 13569)))
                sendMessage(player, "You fill up your glass with " + node.name.lowercase().replace("barrel", "").trim { it <= ' ' } + ".")
                addItem(player, getReward(node.id), 1)
            }
            return@onUseWith true

        }
    }

    fun getReward(barrelId: Int): Int {
        return when (barrelId) {
            13568 -> 1917
            13569 -> 5763
            13570 -> 1905
            13571 -> 1909
            13572 -> 1911
            13573 -> 5755
            else -> 1917
        }
    }

    companion object {
        private val BARREL = intArrayOf(13568, 13569, 13570, 13571, 13572, 13573)
        private const val BEER_GLASS = Items.BEER_GLASS_1919
    }

}