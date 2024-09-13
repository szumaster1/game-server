package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * Represents the windows pane packet context.
 */
class WindowsPaneContext(
    private val player: Player,
    @JvmField val windowId: Int,
    @JvmField val type: Int
) : Context {

    override fun getPlayer(): Player = player
}
