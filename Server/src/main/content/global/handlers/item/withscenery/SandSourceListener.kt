package content.global.handlers.item.withscenery

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse

/**
 * Sand source listener
 *
 * @constructor Sand source listener
 */
class SandSourceListener : InteractionListener {

    companion object {
        private val ANIMATION = Animations.FILL_BUCKET_WITH_SAND_895
        private val SAND_PITS = intArrayOf(Scenery.SAND_PIT_2645, Scenery.SAND_PIT_4373, Scenery.SANDPIT_10814)
        private val SAND_PILE = intArrayOf(Scenery.SAND_2977, Scenery.SAND_2978, Scenery.SAND_2979)
    }

    override fun defineListeners() {

        /*
         * Interaction with sandpits.
         */

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

        /*
         * Interaction with sand piles.
         */

        onUseWith(IntType.SCENERY, Items.BUCKET_1925, *SAND_PILE) { player, used, with ->
            val numEmptyBuckets = amountInInventory(player, used.id)
            var animationTrigger = 0
            runTask(player, 2, numEmptyBuckets) {
                if (removeItem(player, used)) {
                    if (animationTrigger % 2 == 0) {
                        animate(player, ANIMATION)
                    }
                    sendMessage(player, "You fill the bucket with sand.")

                    if (with.id == 2979) {
                        removeScenery(with.asScenery())
                        submitWorldPulse(object : Pulse(75) {
                            override fun pulse(): Boolean {
                                addScenery(
                                    if (inBorders(player, getRegionBorders(11310)))
                                        with.id - 2 else with.id - 1, with.location, with.direction.ordinal
                                )
                                return true
                            }
                        })
                    } else {
                        replaceScenery(with.asScenery(), with.id + 1, 75)
                    }
                    addItem(player, Items.BUCKET_OF_SAND_1783)
                }
                animationTrigger++
            }
            return@onUseWith true
        }

        /*
         * "look" option on sand pile interaction.
         */

        on(SAND_PILE, IntType.SCENERY, "look") { player, node ->
            sendMessage(player, sceneryDefinition(node.id).examine.toString())
            return@on true
        }

    }

}
