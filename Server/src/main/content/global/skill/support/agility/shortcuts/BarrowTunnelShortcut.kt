package content.global.skill.support.agility.shortcuts

import core.api.sendMessage
import core.api.teleport
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

class BarrowTunnelShortcut : InteractionListener {
    override fun defineListeners() {
        on(intArrayOf(5055, 30261, 30262, 30265), IntType.SCENERY, "open") { player, node ->
            when(node.id){
                5055 -> teleport(player, Location(3477, 9845))
                30261,30262 -> teleport(player, Location(3509, 3448))
                30265 -> teleport(player, Location(3500, 9812))
            }
            return@on true
        }

        on(5054, IntType.SCENERY, "climb-up"){ player, _ ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, Location(3496, 3465, 0))
            return@on true
        }

        on(5052, IntType.SCENERY, "search") { player, node ->
            sendMessage(player, "You search the wall and find a lever.")
            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            return@on true
        }


    }
}