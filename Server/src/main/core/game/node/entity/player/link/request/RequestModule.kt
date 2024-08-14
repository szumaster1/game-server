package core.game.node.entity.player.link.request

import core.game.node.entity.player.Player

/**
 * Request module.
 */
interface RequestModule {
    /**
     * Method invoked when the targeting player accepts a request.
     *
     * @param player The player who is accepting the request.
     * @param target The player who is being targeted by the request.
     */
    fun open(player: Player?, target: Player?)
}
