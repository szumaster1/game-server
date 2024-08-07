package content.global.skill.support.construction.decoration.kitchen

import core.api.animate
import core.api.removeItem
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation

/**
 * Cat blanket listener.
 */
class CatBlanketListener : InteractionListener {

    val CAT_BLANKET = intArrayOf(13574, 13575, 13576)
    val PUTTING_CAT_DOWN: Animation = Animation(827)
    val FALLING_ASLEEP: Animation = Animation(2160)
    val SLEEPING: Animation = Animation(2159)
    val CAT_ITEMS = intArrayOf(1561, 1562, 1563, 1564, 1565, 1566)

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, CAT_ITEMS, *CAT_BLANKET) { player, used, _ ->
            animate(player, PUTTING_CAT_DOWN)
            GameWorld.Pulser.submit(object : Pulse(1, player) {
                override fun pulse(): Boolean {
                    if (removeItem(player, used.asItem())) {
                        player.familiarManager.summon((used as Item?), false, true)
                    }
                    return true
                }
            })
            return@onUseWith true
        }
    }
}