package core.game.node.entity.player.link.request

import core.game.node.entity.player.Player

interface RequestModule {
    /*
     * Method invoked when the targeting player accepts a request.
     */
    fun open(player: Player?, target: Player?)
}