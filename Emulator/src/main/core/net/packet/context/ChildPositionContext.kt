package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context
import java.awt.Point

/**
 * A context implementation used for the interface child
 * repositioning packet.
 */
class ChildPositionContext(
    private val player: Player,
    val interfaceId: Int,
    val childId: Int,
    positionX: Int,
    positionY: Int
) : Context {

    val position: Point = Point(positionX, positionY)

    override fun getPlayer(): Player = player
}
