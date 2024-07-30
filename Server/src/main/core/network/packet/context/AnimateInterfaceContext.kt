package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

class AnimateInterfaceContext(
    private val player: Player,
    val animationId: Int,
    val interfaceId: Int,
    val childId: Int
) : Context {

    override fun getPlayer(): Player = player

}
