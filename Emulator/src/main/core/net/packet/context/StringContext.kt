package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The StringPacket packet context.
 */
class StringContext(
    private val player: Player,
    val string: String,
    val interfaceId: Int,
    val lineId: Int
) : Context {

    override fun getPlayer(): Player = player
}
