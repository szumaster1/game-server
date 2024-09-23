package content.global.handlers.item.withitem

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.system.task.Pulse
import org.rs.consts.Animations
import org.rs.consts.Items

/**
 * Handles the oily fishing rod creation.
 */
class OilFishingRod: InteractionListener {

    private val blamishOil = Items.BLAMISH_OIL_1582
    private val fishingRod = Items.FISHING_ROD_307
    private val thinSnail = Items.THIN_SNAIL_3363
    private val pestleAndMortar = Items.PESTLE_AND_MORTAR_233

    override fun defineListeners() {

        /*
         * Creating Oily fishing rod interaction.
         */

        onUseWith(IntType.ITEM, Items.BLAMISH_OIL_1582, Items.FISHING_ROD_307) { player, used, with ->
            submitIndividualPulse(player, object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        1 -> {
                            removeItem(player, used.asItem()) && removeItem(player, with.asItem())
                            addItem(player, Items.VIAL_229)
                            addItem(player, Items.OILY_FISHING_ROD_1585)
                            sendMessage(player, "You rub the oil into the fishing rod.")
                        }
                    }
                    return false
                }
            })
            return@onUseWith true
        }

        /*
         * Creating blamish snail slime.
         */

        onUseWith(IntType.ITEM, Items.THIN_SNAIL_3363, Items.PESTLE_AND_MORTAR_233) { player, _, _ ->
            if (inInventory(player, Items.SAMPLE_BOTTLE_3377, 1)) {

                animate(player, Animations.PESTLE_MORTAR_364)

                queueScript(player, 3, QueueStrength.WEAK) {
                    removeItem(player, Items.THIN_SNAIL_3363)
                    removeItem(player, Items.SAMPLE_BOTTLE_3377)
                    addItem(player, Items.BLAMISH_SNAIL_SLIME_1581)
                    return@queueScript stopExecuting(player)
                }
            }

            return@onUseWith true
        }
    }
}
