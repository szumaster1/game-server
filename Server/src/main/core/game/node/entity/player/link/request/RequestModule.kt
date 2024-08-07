package core.game.node.entity.player.link.request

import core.game.node.entity.player.Player

/**
 * Request module
 *
 * @constructor Request module
 */
interface RequestModule {
    /**
     * Open
     *
     * @param player
     * @param target
     *//*
     * Method invoked when the targeting player accepts a request.
     */
    fun open(player: Player?, target: Player?)
}