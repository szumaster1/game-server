package content.minigame.fistofguthix

import core.game.node.entity.player.Player

/**
 * FOG player.
 */
class FOGPlayer(val player: Player, val target: FOGPlayer) {

    var isHunted = false
    var charges = 0

    /**
     * Switch roles.
     */
    fun switchRoles() {
        isHunted = !isHunted
    }

    /**
     * Increment charges.
     *
     * @param [increment] the amount by which charges should be incremented.
     */
    fun incrementCharges(increment: Int) {
        charges += increment
    }
}