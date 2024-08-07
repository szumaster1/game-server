package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The interface config packet context.
 */
class InterfaceConfigContext(
    private val player: Player,
    val interfaceId: Int,
    val childId: Int,
    val isHidden: Boolean
) : Context {

    override fun getPlayer(): Player = player
}
