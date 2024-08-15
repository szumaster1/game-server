package content.region.fremennik.handlers.waterbirth

import core.api.*
import core.api.consts.Components
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location

/**
 * Waterbirth travel.
 */
object WaterbirthTravel {

    /**
     * Sail
     *
     * @param player The player who is sailing.
     * @param destination The destination where the player is sailing to.
     */
    @JvmStatic
    fun sail(player: Player, destination: TravelDestination) {
        // Lock the player to prevent other actions while sailing
        lock(player, destination.shipAnim + 3)

        // Lock interactions to ensure the player cannot interact with other elements during the animation
        lockInteractions(player, destination.shipAnim)

        // Send a message to the player indicating they are boarding the ship
        sendMessage(player, "You board the longship...")

        // Open a fade-to-black overlay for a smooth transition
        openOverlay(player, Components.FADE_TO_BLACK_115)

        // Open the ship journey interface for the player
        openInterface(player, Components.MISC_SHIPJOURNEY_224)

        // Animate the interface with the specified animation for the journey
        animateInterface(player, Components.MISC_SHIPJOURNEY_224, 7, destination.shipAnim)

        // Teleport the player to the destination location
        teleport(player, destination.destinationLoc)

        // Get the duration of the animation for the ship journey
        val animDuration = animationDuration(getAnimation(destination.shipAnim))

        // Submit a world pulse to handle the arrival message and cleanup
        submitWorldPulse(object : Pulse(animDuration + 3) {
            override fun pulse(): Boolean {
                // Send a message to the player indicating the ship has arrived at the destination
                sendMessage(player, "The ship arrives at ${destination.destName}.")

                // Close the ship journey interface for the player
                closeInterface(player)

                // Close the overlay to return to the normal view
                closeOverlay(player)

                // Unlock the player to allow further actions
                unlock(player)
                return true // Indicate that the pulse is complete
            }
        })
        return // Exit the function
    }

}

/**
 * Travel destination
 *
 * @param destName The name of the travel destination.
 * @param destinationLoc The location coordinates of the destination.
 * @param shipAnim The animation ID for the ship journey.
 * @return travel
 */
enum class TravelDestination(val destName: String, val destinationLoc: Location, val shipAnim: Int) {
    RELLEKA_TO_MISCELLANIA(
        destName = "Miscellania",
        destinationLoc = Location.create(2581, 3845, 0),
        shipAnim = 1372
    ),

    MISCELLANIA_TO_RELLEKKA(
        destName = "Rellekka",
        destinationLoc = Location.create(2629, 3693, 0),
        shipAnim = 1373
    ),
    RELLEKKA_TO_JATIZSO(
        destName = "Jatizso",
        destinationLoc = Location.create(2421, 3781, 0),
        shipAnim = 5766
    ),
    JATIZSO_TO_RELLEKKA(
        destName = "Rellekka",
        destinationLoc = Location.create(2644, 3710, 0),
        shipAnim = 5767
    ),
    RELLEKKA_TO_NEITIZNOT(
        destName = "Neitiznot",
        destinationLoc = Location.create(2310, 3782, 0),
        shipAnim = 5764
    ),
    NEITIZNOT_TO_RELLEKKA(
        destName = "Rellekka",
        destinationLoc = Location.create(2644, 3710, 0),
        shipAnim = 5765
    ),
    WATERBIRTH_TO_RELLEKKA(
        destName = "Rellekka",
        destinationLoc = Location.create(2620, 3685, 0),
        shipAnim = 2345
    ),
    RELLEKKA_TO_WATERBIRTH(
        destName = "Waterbirth Island",
        destinationLoc = Location.create(2544, 3759, 0),
        shipAnim = 2344
    );
}
