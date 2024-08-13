package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The animate interface context.
 * @author Emperor
 *
 * @param player The player associated with the context
 * @param animationId The ID of the animation
 * @param interfaceId The ID of the interface
 * @param childId The ID of the child component
 * @constructor Creates an AnimateInterfaceContext with the specified parameters
 */
class AnimateInterfaceContext(
    private val player: Player,
    val animationId: Int,
    val interfaceId: Int,
    val childId: Int
) : Context {

    override fun getPlayer(): Player = player

}
