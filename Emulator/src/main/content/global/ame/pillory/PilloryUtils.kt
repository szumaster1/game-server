package content.global.ame.pillory

import core.api.clearLogoutListener
import core.api.getAttribute
import core.api.removeAttributes
import core.game.node.entity.player.Player

/**
 * Pillory utils.
 */
object PilloryUtils {
    // Attributes
    val COMPLETE_RANDOM = "pillory:complete"
    val CORRECT_ANSWER = "pillory:correct_answer"
    val WRONG_ANSWER = "pillory:wrong_answer"
    val PILLORY_LOCATION = "/save:original-loc"
    val PILLORY_LOGOUT = "pillory:logout"

    /**
     * Reset player properties after pillory event.
     *
     * @param [player] The player.
     */
    fun cleanup(player: Player) {
        player.properties.teleportLocation = getAttribute(player, PILLORY_LOCATION, null)
        clearLogoutListener(player, PILLORY_LOGOUT)
        removeAttributes(player, COMPLETE_RANDOM, CORRECT_ANSWER, WRONG_ANSWER, PILLORY_LOCATION, PILLORY_LOGOUT)
    }

}
