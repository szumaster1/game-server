package content.region.misthalin.quest.member.losttribe.handlers

import core.api.consts.Components
import core.api.closeInterface
import core.api.lock
import core.api.openInterface
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location

object GoblinFollower {

    fun sendToMines(player: Player) {
        travel(player, Location.create(3319, 9616, 0))
        sendMessage(player, "Kazgar shows you the way through the tunnels.")
    }

    fun sendToLumbridge(player: Player) {
        travel(player, Location.create(3232, 9610, 0))
        sendMessage(player, "Mistag shows you the way through the tunnels.")
    }

    private fun travel(player: Player, location: Location) {
        lock(player, 9)
        GameWorld.Pulser.submit(object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    0 -> openInterface(player, Components.FADE_TO_BLACK_120)
                    6 -> player.properties.teleportLocation = location
                    7 -> closeInterface(player)
                    8 -> openInterface(player, Components.FADE_FROM_BLACK_170)
                    9 -> closeInterface(player).also { return true }
                }
                return false
            }
        })
    }
}
