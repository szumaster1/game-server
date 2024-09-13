package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * Represents the context used for the Minimap State
 * packet.
 */
class MinimapStateContext(
    private val player: Player,
    val state: Int
) : Context {

    override fun getPlayer(): Player = player
}
