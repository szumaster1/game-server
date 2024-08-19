package content.region.misthalin.handlers.edgeville

import core.api.consts.Animations
import core.api.consts.Scenery
import core.api.sendDialogue
import core.api.setVarbit
import core.api.teleport
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Represents the Edgeville listeners.
 */
class EdgevilleListeners : InteractionListener {

    override fun defineListeners() {

        /**
         * Passing through goblin jail to player safety underground.
         */
        on(Scenery.POSTER_29586, IntType.SCENERY, "pull-back") { player, _ ->
            sendDialogue(player, "There appears to be a tunnel behind this poster.")
            teleport(player, Location(3140, 4230, 2))
            return@on true
        }

        /**
         * Trapdoor to Evil Dave location.
         */
        on(Scenery.TRAPDOOR_12267, IntType.SCENERY, "open") { player, _ ->
            setVarbit(player, 1888, 1)
            return@on true
        }

        /**
         * Trapdoor interaction.
         */
        on(Scenery.OPEN_TRAPDOOR_12268, IntType.SCENERY, "close") { player, node ->
            setVarbit(player, 1888, 0)
            return@on true
        }

        /**
         * Trapdoor interaction.
         */
        on(Scenery.OPEN_TRAPDOOR_12268, IntType.SCENERY, "go-down") { player, _ ->
            ClimbActionHandler.climb(player, Animation(Animations.MULTI_USE_BEND_OVER_827), Location(3077, 9893, 0))
            return@on true
        }

        /**
         * Trapdoor interaction.
         */
        on(Scenery.CELLAR_STAIRS_12265, IntType.SCENERY, "climb") { player, _ ->
            ClimbActionHandler.climb(player, null, Location(3078, 3493, 0))
            return@on true
        }
    }

}
