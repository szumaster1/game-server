package core.net.packet.outgoing

import core.net.packet.IoBuffer
import core.net.packet.OutgoingPacket
import core.net.packet.PacketHeader
import core.net.packet.context.ContactContext
import core.tools.StringUtils

/**
 * Handles the contact packet sending.
 * @author Emperor
 */
class ContactPackets : OutgoingPacket<ContactContext> {
    override fun send(context: ContactContext) {
        var buffer: IoBuffer? = null
        val player = context.player
        when (context.type) {
            ContactContext.UPDATE_STATE_TYPE -> {
                /*
                 * Always put the AVAILABLE state.
                 */
                buffer = IoBuffer(197).put(2)
            }

            ContactContext.IGNORE_LIST_TYPE -> {
                buffer = IoBuffer(126, PacketHeader.SHORT)
                for (string in player.communication.blocked) {
                    if (string.isEmpty()) {
                        continue
                    }
                    buffer.putLong(StringUtils.stringToLong(string))
                }
            }

            ContactContext.UPDATE_FRIEND_TYPE -> {
                buffer = IoBuffer(62, PacketHeader.BYTE)
                buffer.putLong(StringUtils.stringToLong(context.name))
                buffer.putShort(context.worldId)
                val c = player.communication.contacts[context.name]
                if (c != null) {
                    buffer.put(c.rank.value.toByte().toInt())
                } else {
                    buffer.put(0)
                }
                if (context.isOnline) {
                    buffer.putString("World $context.worldId")
                }
            }
        }
        if (buffer != null) {
            buffer.cypherOpcode(player.session.isaacPair!!.output)
            player.session.write(buffer)
        }
    }
}

