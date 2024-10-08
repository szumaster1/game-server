package core.net.packet.outgoing

import core.game.world.update.flag.context.Animation
import core.net.packet.IoBuffer
import core.net.packet.OutgoingPacket
import core.net.packet.context.AnimateSceneryContext

/**
 * Represents the packet used to animate an object.
 * @author Emperor
 */
class AnimateScenery : OutgoingPacket<AnimateSceneryContext> {

    override fun send(context: AnimateSceneryContext) {
        val player = context.player
        val `object` = context.animation.getObject()
        val buffer = write(UpdateAreaPosition.getBuffer(player, `object`.location.chunkBase), context.animation)
        buffer.cypherOpcode(context.player.session.isaacPair!!.output)
        player.session.write(buffer)
    }

    companion object {
        @JvmStatic
        fun write(buffer: IoBuffer, animation: Animation): IoBuffer {
            val `object` = animation.getObject()
            val l = `object`.location
            buffer.put(20)
            buffer.putS((l.chunkOffsetX shl 4) or (l.chunkOffsetY and 0x7))
            buffer.putS((`object`.type shl 2) + (`object`.rotation and 0x3))
            buffer.putLEShort(animation.id)
            return buffer
        }
    }
}
