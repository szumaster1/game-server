package content.global.transport.shortcuts

import cfg.consts.Scenery
import core.api.sendMessage
import core.api.teleport
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Represents the Barrow tunnel shortcut.
 */
class BarrowTunnelShortcut : InteractionListener {

    val SHORTCUTS = intArrayOf(
        Scenery.TRAPDOOR_5055,
        Scenery.WOODEN_DOORS_30261,
        Scenery.WOODEN_DOORS_30262,
        Scenery.WOODEN_DOORS_30265
    )

    override fun defineListeners() {
        on(SHORTCUTS, IntType.SCENERY, "open") { player, node ->
            handleShortcut(node.id, player)
            return@on true
        }

        on(Scenery.LADDER_5054, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, Location(3496, 3465, 0))
            return@on true
        }

        on(Scenery.WALL_5052, IntType.SCENERY, "search") { player, node ->
            sendMessage(player, "You search the wall and find a lever.")
            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            return@on true
        }
    }

    private fun handleShortcut(nodeId: Int, player: Player) {
        val destination = when (nodeId) {
            Scenery.TRAPDOOR_5055 -> Location(3477, 9845)
            Scenery.WOODEN_DOORS_30261, Scenery.WOODEN_DOORS_30262 -> Location(3509, 3448)
            Scenery.WOODEN_DOORS_30265 -> Location(3500, 9812)
            else -> return
        }
        teleport(player, destination)
    }
}
