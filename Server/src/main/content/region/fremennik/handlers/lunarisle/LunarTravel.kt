package content.region.fremennik.handlers.lunarisle

import core.api.*
import core.api.consts.Components
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
        lock(player, destination.ladyZayAnim)
        lockInteractions(player, destination.ladyZayAnim)
        openInterface(player, Components.LUNAR_COVE_BOAT_MAP_431)
        animateInterface(player, Components.LUNAR_COVE_BOAT_MAP_431, 1, destination.ladyZayAnim)
        teleport(player, destination.destLoc)
        val animDuration = animationDuration(getAnimation(destination.ladyZayAnim))
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
 * @param destLoc     The location of the destination.
 * @param ladyZayAnim The animation for Lady Zay.
 * @constructor Creates an instance of ReachDestination.
 */
enum class Destinaton(val destName: String, val destLoc: Location, val ladyZayAnim: Int) {
    /**
     * Pirates Cove To Moonclan Island.
     */
    PIRATES_COVE_TO_MOONCLAN_ISLAND("Pirates' Cove", Location.create(2131, 3900, 2), 2344),

    /**
     * Moonclan Island To Pirates Cove.
     */
    MOONCLAN_ISLAND_TO_PIRATES_COVE("Moonclan Island", Location.create(2216, 3797, 2), 2345),
}