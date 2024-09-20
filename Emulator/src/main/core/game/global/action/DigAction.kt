package core.game.global.action

import core.game.node.entity.player.Player

/**
 * Handles a digging reward.
 * @author Emperor
 */
interface DigAction {
    /**
     * Runs the digging reward.
     *
     * @param [player] The player.
     */
    fun run(player: Player?)
}