package core.net.event

import core.api.log
import core.net.IoReadEvent
import core.net.IoSession
import core.net.lobby.WorldList
import core.net.registry.AccountRegister
import core.tools.Log
import core.tools.RandomFunction
import java.nio.ByteBuffer

/**
 * Handles handshake read events.
 * @author Emperor
 */
class HSReadEvent(session: IoSession, buffer: ByteBuffer) : IoReadEvent(session, buffer) {

    override fun read(session: IoSession, buffer: ByteBuffer) {
        val amount = count.getOrDefault(session.address, 0)
        count[session.address] = amount + 1
        when (val opcode = buffer.get().toInt() and 0xFF) {
            14 -> {
                session.nameHash = buffer.get().toInt() and 0xFF
                session.serverKey = RandomFunction.RANDOM.nextLong()
                session.write(true)
            }

            15 -> {
                val revision = buffer.int
                buffer.flip()
                if (revision != 530) {
                    session.disconnect()
                } else {
                    session.write(false)
                }
            }

            in listOf(147, 186, 36) -> AccountRegister.read(session, opcode, buffer)
            255 -> {
                val updateStamp = buffer.int
                WorldList.sendUpdate(session, updateStamp)
            }

            else -> {
                log(this.javaClass, Log.FINE, "PKT $opcode")
                session.disconnect()
            }
        }
    }

    companion object {
        private val count = mutableMapOf<String, Int>()
    }
}
