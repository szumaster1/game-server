package core.network.packet.outgoing

import core.network.packet.IoBuffer
import core.network.packet.OutgoingPacket
import core.network.packet.context.ConfigContext

/**
 * The config outgoing packet.
 * @author Emperor
 */
class Config : OutgoingPacket<ConfigContext> {

    override fun send(context: ConfigContext) {
        val buffer: IoBuffer = if (context.value < Byte.MIN_VALUE || context.value > Byte.MAX_VALUE) {
            IoBuffer(226).apply {
                putInt(context.value)
                putShortA(context.id)
            }
        } else {
            IoBuffer(60).apply {
                putShortA(context.id)
                putC(context.value)
            }
        }

        buffer.cypherOpcode(context.player.session.isaacPair!!.output)

        if (!context.player.isArtificial) {
            context.player.details.session.write(buffer)
        }
    }
}
