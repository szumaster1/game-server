package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The interface packet context.
 */
class InterfaceContext(
    private var player: Player,
    val windowId: Int,
    var componentId: Int,
    val interfaceId: Int,
    val isWalkable: Boolean
) : Context {

    fun transform(player: Player, id: Int): InterfaceContext {
        return InterfaceContext(player, windowId, componentId, id, isWalkable)
    }

    override fun getPlayer(): Player = player

    fun setPlayer(player: Player): Context {
        this.player = player
        return this
    }
}
