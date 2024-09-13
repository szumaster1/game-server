package core.net.packet.context

import core.game.node.entity.player.Player
import core.game.world.map.RegionChunk
import core.net.packet.Context

/**
 * The packet context for the clear region chunk
 * outgoing packet.
 */
class ClearChunkContext(
    private val player: Player, // Holds the reference to the player associated with the packet context.
    @JvmField val chunk: RegionChunk // Represents the region chunk to be cleared.
) : Context {

    override fun getPlayer(): Player = player
}
