package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The animate interface context.
 * @author Emperor
 *
 * @param [player]          the player.
 * @param [animationId]     the animation.
 * @param [interfaceId]     the interface.
 * @param [childId]         the child component.
 * @constructor Creates an [AnimateInterfaceContext].
 */
class AnimateInterfaceContext(
    private val player: Player,
    val animationId: Int,
    val interfaceId: Int,
    val childId: Int
) : Context {

    override fun getPlayer(): Player = player

}
