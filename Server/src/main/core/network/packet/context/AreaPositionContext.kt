package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.network.packet.Context

/**
 * Handles the area position update packet context.
 * @author Emperor
 */
class AreaPositionContext(
    private val player: Player,
    @JvmField val location: Location,
    val offsetX: Int,
    val offsetY: Int
) : Context {

    override fun getPlayer(): Player = player
}
