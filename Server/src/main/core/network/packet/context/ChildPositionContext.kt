package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context
import java.awt.Point

/**
 * A context implementation used for the interface child
 * repositioning packet.
 */
class ChildPositionContext(
    private val player: Player, // Player associated with the context
    val interfaceId: Int, // ID of the interface
    val childId: Int, // ID of the child element
    positionX: Int, // X-coordinate of the child element
    positionY: Int // Y-coordinate of the child element
) : Context {

    val position: Point = Point(positionX, positionY) // Position of the child element

    override fun getPlayer(): Player = player // Returns the player associated with the context
}
