package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The access mask context.
 * @author Emperor
 */
class AccessMaskContext(
    private var player: Player,
    val id: Int,
    val childId: Int,
    val interfaceId: Int,
    val offset: Int,
    val length: Int
) : Context {

    fun transform(player: Player, id: Int): AccessMaskContext {
        return AccessMaskContext(player, id, childId, interfaceId, offset, length)
    }

    fun setPlayer(player: Player): Context {
        this.player = player
        return this
    }

    override fun getPlayer(): Player = player

}
