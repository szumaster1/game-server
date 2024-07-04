package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * Represents the context used for the Minimap State
 * packet.
 * @author Emperor
 */
class MinimapStateContext(
    private val player: Player,
    val state: Int
) : Context {

    override fun getPlayer(): Player = player
}
