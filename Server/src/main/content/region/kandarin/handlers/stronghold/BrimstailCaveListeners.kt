package content.region.kandarin.handlers.stronghold

import content.global.travel.EssenceTeleport
import cfg.consts.NPCs
import cfg.consts.Scenery
import core.api.sendDialogue
import core.api.sendNPCDialogue
import core.api.teleport
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

/**
 * Brimstail cave listeners.
 */
class BrimstailCaveListeners : InteractionListener {

    companion object {
        private val CAVE_EXIT = intArrayOf(Scenery.TUNNEL_17222, Scenery.TUNNEL_17223)
        private const val CAVE_ENTER = Scenery.CAVE_ENTRANCE_17209
        private const val BRIMSTAIL = NPCs.BRIMSTAIL_171
        private const val TABLE = Scenery.TABLE_17235
        private const val ASPIDISTRA_PLANT = Scenery.ASPIDISTRA_PLANT_17238
    }

    override fun defineListeners() {

        // Listener for entering the cave
        on(CAVE_ENTER, IntType.SCENERY, "enter") { player, _ ->
            teleport(player, Location.create(2408, 9812, 0))
            return@on true
        }

        // Listener for exiting the cave
        on(CAVE_EXIT, IntType.SCENERY, "exit") { player, _ ->
            teleport(player, Location(2402, 3419, 0))
            return@on true
        }

        // Listener for interacting with the table
        on(TABLE, IntType.SCENERY, "take-bowl") { player, _ ->
            sendNPCDialogue(player, BRIMSTAIL, "Stop, I don't want you to spill water on my books!", FacialExpression.OLD_ANGRY1)
            return@on true
        }

        // Listener for searching the aspidistra plant
        on(ASPIDISTRA_PLANT, IntType.SCENERY, "search") { player, _ ->
            sendDialogue(player, "Gronda Gronda!")
            return@on true
        }

        // Listener for teleporting with Brimstail
        on(BRIMSTAIL, IntType.NPC, "teleport") { player, node ->
            EssenceTeleport.teleport(node.asNpc(), player)
            return@on true
        }

    }

}
