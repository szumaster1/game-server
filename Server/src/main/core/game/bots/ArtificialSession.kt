package core.game.bots

import core.network.IoSession
import java.nio.ByteBuffer

/**
 * Represents an artificial networking session.
 */
class ArtificialSession private constructor() : IoSession(null, null) {

    override fun getRemoteAddress(): String {
        return "127.0.0.1"
    }

    override fun write(context: Any, instant: Boolean) {
    }

    override fun queue(buffer: ByteBuffer) {
    }

    override fun write() {
    }

    override fun disconnect() {
    }

    companion object {
        val singleton: ArtificialSession = ArtificialSession()
    }
}
