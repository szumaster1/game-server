package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.system.communication.ClanRepository
import core.network.packet.Context

/**
 * The packet context for clan-related
 * outgoing packets.
 * @author Emperor
 */
class ClanContext(
    private val player: Player,
    @JvmField val clan: ClanRepository,
    val isLeave: Boolean
) : Context {

    override fun getPlayer(): Player = player
}
