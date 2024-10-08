package content.region.wilderness.handlers

import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import org.rs.consts.Animations
import org.rs.consts.Scenery

class WildernessLadderListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles ladder to lava maze (KBD Lever).
         */

        on(Scenery.LADDER_1765, IntType.SCENERY, "climb-down") { player, _ ->
            ClimbActionHandler.climb(player, null, Location.create(3069, 10257, 0))
            return@on true
        }

        /*
         * Handles ladder to lava maze.
         */

        on(Scenery.LADDER_1767, IntType.SCENERY, "climb-down") { player, _ ->
            ClimbActionHandler.climb(player, null, Location.create(3017, 10250, 0))
            return@on true
        }

        /*
         * Handles ladder to lava maze.
         */

        on(Scenery.LADDER_32015, IntType.SCENERY, "climb-up") { player, node ->
            val ladder = node.asScenery()
            ClimbActionHandler.climb(
                player, Animation(Animations.USE_LADDER_828), if (ladder.location.x == 3069)
                    Location.create(3017, 3848, 0) else Location.create(3069, 3855, 0)
            )
            return@on true
        }

    }

}