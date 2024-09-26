package content.region.fremennik.lunarisle

import org.rs.consts.Components
import core.api.*
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location

/**
 * Represents the Lunar travel utils.
 */
object LunarTravelUtils {

    /**
     * Function to sail the player to a destination.
     *
     * @param player The player to sail.
     * @param destination The destination to reach.
     */
    @JvmStatic
    fun sail(player: Player, destination: Destinaton) {
        lock(player, destination.animation)
        lockInteractions(player, destination.animation)
        openInterface(player, Components.LUNAR_COVE_BOAT_MAP_431)
        animateInterface(player, Components.LUNAR_COVE_BOAT_MAP_431, 1, destination.animation)
        teleport(player, destination.destination)
        val animDuration = animationDuration(getAnimation(destination.animation))
        submitWorldPulse(object : Pulse(animDuration) {
            override fun pulse(): Boolean {
                closeInterface(player)
                closeOverlay(player)
                unlock(player)
                return true
            }
        })
        return
    }
}

enum class Destinaton(val destName: String, val destination: Location, val animation: Int) {
    PIRATES_COVE_TO_MOONCLAN_ISLAND(
        destName = "Pirates' Cove",
        destination = Location.create(2131, 3900, 2),
        animation = 2344
    ),
    MOONCLAN_ISLAND_TO_PIRATES_COVE(
        destName = "Moonclan Island",
        destination = Location.create(2216, 3797, 2),
        animation = 2345
    ),
}