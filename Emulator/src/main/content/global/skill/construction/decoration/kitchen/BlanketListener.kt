package content.global.skill.construction.decoration.kitchen

import content.global.skill.summoning.pet.Pets
import core.api.animate
import core.api.lock
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.Scenery

/**
 * Handles interactions for placing a cat on a blanket.
 */
class BlanketListener : InteractionListener {

    private val petItemIDs = intArrayOf(
        Items.PET_CAT_1561,
        Items.PET_CAT_1562,
        Items.PET_CAT_1563,
        Items.PET_CAT_1564,
        Items.PET_CAT_1565,
        Items.PET_CAT_1566
    )

    private val blanketIDs = intArrayOf(
        Scenery.PET_BLANKET_13574,
        Scenery.PET_BASKET_13575,
        Scenery.PET_BASKET_13576
    )

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, petItemIDs, *blanketIDs) { player, used, scenery ->
            val item = used as? Item ?: return@onUseWith false
            val familiar = player.familiarManager.familiar
            if (player.houseManager.isBuildingMode) {
                sendMessage(player, "You cannot do this in building mode.")
                return@onUseWith false
            }
            if (player.familiarManager.hasFamiliar()) {
                return@onUseWith false
            }
            lock(player, 1)
            animate(player, Animation(Animations.MULTI_USE_BEND_OVER_827))
            if (Pets.forId(item.id) != null) {
                player.familiarManager.morphPet(item, !player.isAdmin, scenery.location)
            }
            GameWorld.Pulser.submit(object : Pulse() {
                var counter = 0

                override fun pulse(): Boolean {
                    return when (counter++) {
                        0 -> {
                            familiar.lock()
                            familiar.sendChat("Meeeew!")
                            familiar.animate(Animation(2160))
                            false
                        }

                        1 -> {
                            familiar.animate(Animation(2159))
                            true
                        }

                        else -> true
                    }
                }
            })
            return@onUseWith true
        }
    }
}
