package content.region.misthalin.handlers.edgeville

import core.api.consts.Scenery
import core.api.sendDialogue
import core.api.teleport
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

class EdgevilleListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Passing through goblin jail to player safety underground.
         */

        on(Scenery.POSTER_29586, IntType.SCENERY, "pull-back") { player, _ ->
            sendDialogue(player, "There appears to be a tunnel behind this poster.")
            teleport(player, Location(3140, 4230, 2))
            return@on true
        }
    }

}
