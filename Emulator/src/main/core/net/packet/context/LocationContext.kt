package core.net.packet.context

import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.net.packet.Context

/**
 * Packet context used for location based packets.
 */
class LocationContext(
    private val player: Player,
    @JvmField val location: Location,
    val isTeleport: Boolean
) : Context {

    override fun getPlayer(): Player = player
}
