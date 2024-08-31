package content.global.random.event.pillory

import core.api.clearLogoutListener
import core.api.getAttribute
import core.api.removeAttributes
import core.game.node.entity.player.Player

/**
 * Pillory utils.
 */
object PilloryUtils {
    // Attributes
    val COMPLETE_RANDOM = "pillory:complete" // Complete pillory event
    val CORRECT_ANSWER = "pillory:correct_answer" // Correct answer in pillory event
    val WRONG_ANSWER = "pillory:wrong_answer" // Wrong answer in pillory event
    val PILLORY_LOCATION = "/save:original-loc" // Key for saving original location during pillory event
    val PILLORY_LOGOUT = "pillory:logout" // Key for pillory logout

    /**
     * Cleanup function to reset player properties after pillory event.
     *
     * @param player The player whose properties need to be cleaned up.
     */
    fun cleanup(player: Player) {
        player.properties.teleportLocation = getAttribute(player, PILLORY_LOCATION, null) // Reset player's teleport location
        clearLogoutListener(player, PILLORY_LOGOUT) // Clear logout listener specific to pillory event
        removeAttributes(player, COMPLETE_RANDOM, CORRECT_ANSWER, WRONG_ANSWER, PILLORY_LOCATION, PILLORY_LOGOUT) // Remove pillory-related attributes
    }

}
