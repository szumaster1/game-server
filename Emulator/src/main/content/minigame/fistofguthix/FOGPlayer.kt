package content.minigame.fistofguthix

import core.game.node.entity.player.Player

/**
 * FOG player.
 */
class FOGPlayer(val player: Player, val target: FOGPlayer) {

    var isHunted = false // Flag to indicate if the player is being hunted
    var charges = 0 // Number of charges the player has

    /**
     * Switch roles
     *
     */
    fun switchRoles() {
        isHunted = !isHunted // Toggle the hunted status of the player
    }

    /**
     * Increment charges
     *
     * @param increment the amount by which charges should be incremented
     */
    fun incrementCharges(increment: Int) {
        charges += increment // Increase the charges by the specified increment
    }
}