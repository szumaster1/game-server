package content.global.skill.construction.decoration.bedroom

import core.api.animate
import core.api.lock
import core.api.openInterface
import core.api.submitIndividualPulse
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import org.rs.consts.Components
import org.rs.consts.Scenery

/**
 * Wardrobe listener.
 */
class WardrobeListener: InteractionListener {

    private val wardrobeIds =
        intArrayOf(Scenery.SHOE_BOX_13155, Scenery.OAK_DRAWERS_13156, Scenery.GILDED_WARDROBE_13161)

    override fun defineListeners() {
        on(wardrobeIds, IntType.SCENERY, "change-clothes") { player, node ->
            lock(player, 2)
            submitIndividualPulse(player, object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> animate(player, 535)
                        2 -> {
                            if (node.id == Scenery.SHOE_BOX_13155) {
                                openInterface(player, Components.YRSA_SHOE_STORE_200)
                            } else {
                                openInterface(
                                    player, if (player.appearance.isMale) {
                                        Components.THESSALIA_CLOTHES_MALE_591
                                    } else {
                                        Components.THESSALIA_CLOTHES_FEMALE_594
                                    }
                                )
                            }
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }
    }
}