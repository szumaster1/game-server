package content.region.desert.handlers

import core.api.*
import cfg.consts.Components
import cfg.consts.Scenery
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Represents the Sophanem listeners.
 */
class SophanemListeners : InteractionListener {

    companion object {
        private const val LADDER_UP = Scenery.LADDER_20277
        private const val LADDER_DOWN = Scenery.LADDER_20275
    }

    override fun defineListeners() {
        /*
         * Handling the action when a player climbs up the ladder.
         */

        on(LADDER_UP, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, Animation(828), Location(3315, 2796, 0))
            return@on true
        }

        /*
         * Handling the action when player climbs down the ladder.
         */

        on(LADDER_DOWN, IntType.SCENERY, "climb-down") { player, _ ->
            if (!hasRequirement(player, "Contact!")) return@on true
            ClimbActionHandler.climb(player, Animation(827), Location(2799, 5160, 0))
            return@on true
        }

        /*
         * Handling quest related location, available just for unlocking the music.
         */

        on(Scenery.DOOR_6614, IntType.SCENERY, "open") { player, _ ->
            lock(player, 3)
            openInterface(player, Components.FADE_TO_BLACK_115)
            runTask(player, 2){
                teleport(player, Location.create(3277, 9171, 0), TeleportManager.TeleportType.INSTANT)
            }
            return@on true
        }
    }

}
