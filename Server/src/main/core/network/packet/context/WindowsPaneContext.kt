package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * Represents the windows pane packet context.
 * @author Emperor
 */
class WindowsPaneContext(
    private val player: Player,
    @JvmField val windowId: Int,
    @JvmField val type: Int
) : Context {

    override fun getPlayer(): Player = player
}
