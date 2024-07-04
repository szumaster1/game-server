package core.network.packet.outgoing

import core.network.packet.IoBuffer
import core.network.packet.OutgoingPacket
import core.network.packet.context.DisplayModelContext

/**
 * Represents the outgoing packet for the displaying of a
 * node model on an interface.
 * @author Emperor
 */
class DisplayModel : OutgoingPacket<DisplayModelContext> {

    override fun send(context: DisplayModelContext) {
        val buffer = when (context.type) {
            DisplayModelContext.ModelType.PLAYER -> {
                IoBuffer(66).apply {
                    putLEShortA(context.player.interfaceManager.getPacketCount(1))
                    putIntA(context.interfaceId shl 16 or context.childId)
                }
            }
            DisplayModelContext.ModelType.NPC -> {
                IoBuffer(73).apply {
                    putShortA(context.nodeId)
                    putLEInt(context.interfaceId shl 16 or context.childId)
                    putLEShort(context.player.interfaceManager.getPacketCount(1))
                }
            }
            DisplayModelContext.ModelType.ITEM -> {
                val value = if (context.amount > 0) context.amount else context.zoom
                IoBuffer(50).apply {
                    putInt(value)
                    putIntB(context.interfaceId shl 16 or context.childId)
                    putLEShortA(context.nodeId)
                    putLEShort(context.player.interfaceManager.getPacketCount(1))
                }
            }
            DisplayModelContext.ModelType.MODEL -> {
                IoBuffer(130).apply {
                    putLEInt(context.interfaceId shl 16 or context.childId)
                    putLEShortA(context.player.interfaceManager.getPacketCount(1))
                    putShortA(context.nodeId)
                }
            }
            else -> return
        }
        buffer.cypherOpcode(context.player.session.isaacPair.output)
        context.player.session.write(buffer)
    }
}

