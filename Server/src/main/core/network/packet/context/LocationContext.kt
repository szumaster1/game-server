package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.network.packet.Context

/**
 * Packet context used for location based packets.
 * @author Emperor
 */
class LocationContext(
    private val player: Player,
    @JvmField val location: Location,
    val isTeleport: Boolean
) : Context {

    override fun getPlayer(): Player = player
}
