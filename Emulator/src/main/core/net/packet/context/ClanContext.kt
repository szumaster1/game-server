package core.net.packet.context

import core.game.node.entity.player.Player
import core.game.system.communication.ClanRepository
import core.net.packet.Context

/**
 * The packet context for clan-related
 * outgoing packets.
 */
class ClanContext(
    private val player: Player, // Define a private property to store the player associated with the context
    @JvmField val clan: ClanRepository, // Define a public property to store the clan repository
    val isLeave: Boolean // Define a property to indicate if the player is leaving the clan
) : Context {

    override fun getPlayer(): Player = player // Return the player associated with the context
}
