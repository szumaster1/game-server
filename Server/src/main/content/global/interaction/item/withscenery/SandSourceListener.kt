package content.global.interaction.item.withscenery

import core.api.*
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.update.flag.context.Animation

class SandSourceListener : InteractionListener {

    companion object {
        private val ANIMATION = Animation(895)
        private val SAND_PITS = intArrayOf(Scenery.SAND_PIT_2645, Scenery.SAND_PIT_4373, Scenery.SANDPIT_10814)
    }

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.BUCKET_1925, *SAND_PITS) { player, used, _ ->
            val numEmptyBuckets = amountInInventory(player, used.id)

            var animationTrigger = 0
            runTask(player, 2, numEmptyBuckets) {
                if (removeItem(player, used)) {
                    if (animationTrigger % 2 == 0) {
                        animate(player, ANIMATION)
                    }
                    sendMessage(player, "You fill the bucket with sand.")
                    addItem(player, Items.BUCKET_OF_SAND_1783)
                }
                animationTrigger++
            }
            return@onUseWith true
        }
    }

}