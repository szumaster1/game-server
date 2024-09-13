package core.net.packet.outgoing

import core.net.packet.IoBuffer
import core.net.packet.OutgoingPacket
import core.net.packet.PacketHeader
import core.net.packet.context.GrandExchangeContext

/**
 * Grand exchange packet.
 * @author Emperor
 */
class GrandExchangePacket : OutgoingPacket<GrandExchangeContext> {
    private val removed = 6
    private val aborted = 2

    override fun send(context: GrandExchangeContext) {
        val buffer = IoBuffer(116, PacketHeader.NORMAL)
        buffer.put(context.idx.toInt())
        if (context.state.toInt() == removed) {
            buffer.put(0).putShort(0).putInt(0).putInt(0).putInt(0).putInt(0)
        } else {
            var state = (context.state + 1).toByte()
            if (context.isSell) {
                state = (state + 8).toByte()
            }
            if (context.state.toInt() == aborted) {
                state = if (context.isSell) (-3).toByte() else 5.toByte()
            }
            buffer.put(state.toInt()).putShort(context.itemID.toInt()).putInt(context.value).putInt(context.amt)
                .putInt(context.completedAmt).putInt(context.totalCoinsExchanged)
        }
        try {
            buffer.cypherOpcode(context.player.session.isaacPair!!.output)
            context.player.session.write(buffer)
        } catch (e: Exception) {
            // Do nothing
        }
    }
}

