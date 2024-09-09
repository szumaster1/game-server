package content.region.fremennik.lunar_isle.handlers

import cfg.consts.Components
import core.api.*
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location

/**
 * Represents the Lunar travel utils.
 */
object LunarTravel {

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

/**
 * Represents the Enum class representing reaching a destination.
 *
 * @param destName    The name of the destination.
 * @param destination The location of the destination.
 * @param animation   The animation for Lady Zay.
 * @constructor Creates an instance of ReachDestination.
 */
enum class Destinaton(val destName: String, val destination: Location, val animation: Int) {
    /**
     * Pirates Cove To Moonclan Island.
     */
    PIRATES_COVE_TO_MOONCLAN_ISLAND("Pirates' Cove", Location.create(2131, 3900, 2), 2344),

    /**
     * Moonclan Island To Pirates Cove.
     */
    MOONCLAN_ISLAND_TO_PIRATES_COVE("Moonclan Island", Location.create(2216, 3797, 2), 2345),
}