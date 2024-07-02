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
         * Never saw it, but I don't give a fuck.
         */

        on(Scenery.POSTER_29586, IntType.SCENERY, "pull-back") { player, _ ->
            sendDialogue(player, "There appears to be a tunnel behind this poster.")
            teleport(player, Location(3140, 4230, 2))
            return@on true
        }
    }

}
