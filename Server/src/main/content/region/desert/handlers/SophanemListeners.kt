package content.region.desert.handlers

import core.api.consts.Scenery
import core.api.hasRequirement
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

class SophanemListeners : InteractionListener {

    companion object {
        private const val LADDER_UP = Scenery.LADDER_20277
        private const val LADDER_DOWN = Scenery.LADDER_20275
    }

    override fun defineListeners() {
        on(LADDER_UP, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, Animation(828), Location(3315, 2796, 0))
            return@on true
        }

        on(LADDER_DOWN, IntType.SCENERY, "climb-down") { player, _ ->
            if (!hasRequirement(player, "Contact!")) return@on true
            ClimbActionHandler.climb(player, Animation(827), Location(2799, 5160, 0))
            return@on true
        }
    }

}