package core.network.event

import core.network.IoSession
import core.network.IoWriteEvent
import core.network.auth.AuthResponse
import core.network.producer.GameEventProducer
import java.nio.ByteBuffer

/**
 * Handles login writing events.
 * @author Emperor
 */
class LoginWriteEvent(session: IoSession, context: Any) : IoWriteEvent(session, context) {

    companion object {
        private val GAME_PRODUCER = GameEventProducer()
    }

    override fun write(session: IoSession, context: Any) {
        val response = context as AuthResponse
        val buffer = ByteBuffer.allocate(500)
        buffer.put(response.ordinal.toByte())
        when (response.ordinal) {
            2 -> { /*
                    * successful login.
                    */
                buffer.put(getWorldResponse(session))
                session.producer = GAME_PRODUCER
            }

            21 -> { /*
                     * Moving world.
                     */
                buffer.put(session.serverKey.toByte())
            }
        }
        buffer.flip()
        session.queue(buffer)
    }

    private fun getWorldResponse(session: IoSession): ByteBuffer {
        val buffer = ByteBuffer.allocate(150)
        val player = session.player
        buffer.put(player!!.details.rights.ordinal.toByte())
        buffer.put(0)
        buffer.put(0)
        buffer.put(0)
        buffer.put(1)
        buffer.put(0)
        buffer.put(0)
        buffer.putShort(player.index.toShort())
        buffer.put(1) // Enable all G.E boxes
        buffer.put(1)
        buffer.flip()
        return buffer
    }
}
