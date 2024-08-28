package content.global.handlers.areas.trollheim

import cfg.consts.Scenery
import core.api.getRegionBorders
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

/**
 * Represents the Trollheim listeners.
 */
class TrollheimListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Handle cave entrances.
         */

        on(intArrayOf(Scenery.CAVE_ENTRANCE_3759, Scenery.CAVE_ENTRANCE_3735), IntType.SCENERY, "enter") { player, node ->
            if(node.id == Scenery.CAVE_ENTRANCE_3759){
                player.properties.teleportLocation = Location.create(2893, 10074, 0)
            } else {
                player.properties.teleportLocation = Location.create(2269, 4752, 0)
            }
            return@on true
        }

        /*
         * Handle exit from the cave.
         */

        on(Scenery.CAVE_EXIT_32738, IntType.SCENERY, "exit") { player, _ ->
            if (!getRegionBorders(11677).insideBorder(player)) {
                player.properties.teleportLocation = Location.create(2858, 3577, 0)
            } else {
                player.properties.teleportLocation = Location.create(2893, 3671, 0)
            }
            return@on true
        }
    }

    override fun defineDestinationOverrides() {
        setDest(IntType.SCENERY, intArrayOf(Scenery.CAVE_ENTRANCE_3759), "enter") { _, _ ->
            return@setDest Location.create(2893, 3671, 0)
        }
    }
}