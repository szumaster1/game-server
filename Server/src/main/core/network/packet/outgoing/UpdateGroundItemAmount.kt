package core.network.packet.outgoing

import core.game.node.item.Item
import core.network.packet.IoBuffer
import core.network.packet.OutgoingPacket
import core.network.packet.context.BuildItemContext

/**
 * Updates the ground item amount.
 * @author Emperor
 */
class UpdateGroundItemAmount : OutgoingPacket<BuildItemContext> {

    /**
     * Writes the packet.
     */
    override fun send(context: BuildItemContext) {
        val player = context.player
        val item = context.item
        val buffer = write(UpdateAreaPosition.getBuffer(player, item.location.chunkBase), item, context.oldAmount)
        buffer.cypherOpcode(context.player.session.isaacPair!!.output)
        player.details.session.write(buffer)
    }

    companion object {

        @JvmStatic
        fun write(buffer: IoBuffer, item: Item, oldAmount: Int): IoBuffer {
            val l = item.location
            buffer.put(14).put((l.chunkOffsetX shl 4) or (l.chunkOffsetY and 0x7)).putShort(item.id).putShort(oldAmount)
                .putShort(item.amount)
            return buffer
        }
    }
}
