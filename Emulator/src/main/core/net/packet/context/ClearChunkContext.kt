package core.net.packet.context

import core.game.node.entity.player.Player
import core.game.world.map.RegionChunk
import core.net.packet.Context

/**
 * The packet context for the clear region chunk
 * outgoing packet.
 */
class ClearChunkContext(
    private val player: Player,
    @JvmField val chunk: RegionChunk
) : Context {

    override fun getPlayer(): Player = player
}
