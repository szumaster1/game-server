package core.network.packet

import core.api.log
import core.api.tryPop
import core.network.packet.outgoing.*
import core.tools.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

/**
 * Packet write queue.
 *
 * @constructor Initializes the PacketWriteQueue class.
 */
class PacketWriteQueue {
    companion object {
        private val packetsToWrite = LinkedList<QueuedPacket<*>>()

        /**
         * Handles the outgoing packet.
         *
         * @param packet The outgoing packet to handle.
         * @param context The context in which the packet is being handled.
         */
        @JvmStatic
        fun <T> handle(packet: OutgoingPacket<T>, context: T) {
            when (packet) {
                // Dynamic packets need to be sent immediately
                is UpdateSceneGraph,
                is BuildDynamicScene,
                is InstancedLocationUpdate,
                is LogoutPacket,
                is ClearRegionChunk -> packet.send(context)
                // Rest get queued up and sent at the end of the tick (authentic)
                else -> push(packet, context)
            }
        }

        /**
         * Pushes the outgoing packet to the write queue.
         *
         * @param packet The outgoing packet to push.
         * @param context The context in which the packet is being pushed.
         */
        @JvmStatic
        fun <T> push(packet: OutgoingPacket<T>, context: T) {
            if (context == null) {
                log(this::class.java, Log.ERR, "${packet::class.java.simpleName} tried to queue with a null context!")
                return
            }
            packetsToWrite.add(QueuedPacket(packet, context))
        }

        /**
         * Flushes the write queue by sending all the packets.
         */
        @JvmStatic
        fun flush() {
            var countThisCycle = packetsToWrite.size
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            while (countThisCycle-- > 0) {
                val pkt = packetsToWrite.tryPop(null) ?: continue
                try {
                    write(pkt.out, pkt.context)
                } catch (e: Exception) {
                    e.printStackTrace(pw)
                    log(this::class.java, Log.ERR, "Error flushing packet ${pkt.out::class.java}: $sw")
                    continue
                }
            }
        }

        /**
         * Writes the outgoing packet to the context.
         *
         * @param out The outgoing packet to write.
         * @param context The context in which the packet is being written.
         * @throws IllegalStateException if the packet or context casting fails.
         */
        @Suppress("UNCHECKED_CAST")
        fun <T> write(out: OutgoingPacket<*>, context: T) {
            val pack = out as? OutgoingPacket<T>
            val ctx = context as? T
            if (pack == null || ctx == null) {
                throw IllegalStateException("Failed packet casting")
            }
            pack.send(ctx)
        }
    }
}

/**
 * Queued packet
 *
 * @param T The type of the packet.
 * @property out The outgoing packet.
 * @property context The context in which the packet is being handled.
 * @constructor Initializes the QueuedPacket class.
 */
class QueuedPacket<T>(val out: OutgoingPacket<T>, val context: T)
