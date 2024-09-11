package content.global.skill.support.construction.decoration.kitchen

import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Scenery
import content.global.skill.support.construction.decoration.kitchen.CatblanketNPC.Companion.spawnCat
import core.api.animate
import core.api.lock
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.update.flag.context.Animation

/**
 * Handle put the cat on blanket interactions.
 */
class CatBlanketListener : InteractionListener {

    private val catNPCsIDs = intArrayOf(NPCs.CAT_768, NPCs.CAT_769, NPCs.CAT_770, NPCs.CAT_771, NPCs.CAT_772, NPCs.CAT_773)
    private val petItemIDs = intArrayOf(Items.PET_CAT_1561, Items.PET_CAT_1562, Items.PET_CAT_1563, Items.PET_CAT_1564, Items.PET_CAT_1565, Items.PET_CAT_1566)
    private val blanketIDs = intArrayOf(Scenery.PET_BLANKET_13574, Scenery.PET_BASKET_13575, Scenery.PET_BASKET_13576)

    override fun defineListeners() {

        onUseWith(IntType.SCENERY, petItemIDs, *blanketIDs) { player, _, scenery ->
            if (player.houseManager.isBuildingMode) {
                sendMessage(player, "You cannot do this in building mode.")
                return@onUseWith false
            }
            lock(player, 1)
            animate(player, Animation(Animations.MULTI_USE_BEND_OVER_827))
            spawnCat(player, scenery.location)
            return@onUseWith true
        }
    }
}