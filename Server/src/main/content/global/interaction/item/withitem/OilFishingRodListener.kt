package content.global.interaction.item.withitem

import core.api.addItem
import core.api.consts.Items
import core.api.inInventory
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation

class OilFishingRodListener : InteractionListener {

    private val blamishOil = Items.BLAMISH_OIL_1582
    private val fishingRod = Items.FISHING_ROD_307
    private val thinSnail = Items.THIN_SNAIL_3363
    private val pestleAndMortar = Items.PESTLE_AND_MORTAR_233

    override fun defineListeners() {
        onUseWith(IntType.ITEM, blamishOil, fishingRod) { player, used, with ->
            player.pulseManager.run(object : Pulse() {
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

        onUseWith(IntType.ITEM, thinSnail, pestleAndMortar) { player, _, _ ->
            if (inInventory(player, Items.SAMPLE_BOTTLE_3377, 1)) {
                player.pulseManager.run(object : Pulse() {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0 -> player.animator.animate(Animation(364))
                            3 -> {
                                removeItem(player, Items.THIN_SNAIL_3363)
                                removeItem(player, Items.SAMPLE_BOTTLE_3377)
                                addItem(player, Items.BLAMISH_SNAIL_SLIME_1581)
                            }
                        }
                        return false
                    }
                })
            }
            return@onUseWith true
        }
    }
}