package core.net.event

import core.api.log
import core.game.world.GameWorld
import core.net.IoReadEvent
import core.net.IoSession
import core.net.amsc.ManagementServerState
import core.net.amsc.WorldCommunicator
import core.net.producer.MSEventProducer
import core.tools.Log
import java.nio.ByteBuffer

/**
 * Registry read event.
 * @author Emperor
 *
 * @param session the session.
 * @param buffer the buffer.
 */
class RegistryReadEvent(session: IoSession, buffer: ByteBuffer) : IoReadEvent(session, buffer) {

    companion object {
        private val PRODUCER = MSEventProducer()
    }

    override fun read(session: IoSession, buffer: ByteBuffer) {
        val opcode = buffer.get().toInt() and 0xFF
        when (opcode) {
            0 -> {
                WorldCommunicator.setState(ManagementServerState.NOT_AVAILABLE)
                log(this.javaClass, Log.ERR, "Failed registering world to AMS - [id=${GameWorld.settings?.worldId}, cause=World id out of bounds]!")
            }

            1 -> {
                session.producer = PRODUCER
                WorldCommunicator.setState(ManagementServerState.AVAILABLE)
            }

            2, 3 -> WorldCommunicator.setState(ManagementServerState.NOT_AVAILABLE)
            else -> {
                /*
                 * Handle default case.
                 */
            }
        }
    }
}