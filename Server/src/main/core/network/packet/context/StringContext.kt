package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The StringPacket packet context.
 * @author Emperor
 */
class StringContext(
    private val player: Player,
    val string: String,
    val interfaceId: Int,
    val lineId: Int
) : Context {

    override fun getPlayer(): Player = player
}
