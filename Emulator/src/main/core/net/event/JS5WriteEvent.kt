package core.net.event

import core.cache.Cache
import core.net.IoSession
import core.net.IoWriteEvent
import java.nio.ByteBuffer

/**
 * Handles JS-5 writing events.
 * @author Emperor
 */
class JS5WriteEvent(session: IoSession, context: Any) : IoWriteEvent(session, context) {

    override fun write(session: IoSession, context: Any) {
        val request = context as IntArray
        val container = request[0]
        val archive = request[1]
        val priority = request[2] == 1
        if (archive == 255 && container == 255) {
            session.queue(getReferenceData())
            return
        }
        val response = Cache.getArchiveData(container, archive, priority, session.js5Encryption)
        response?.let { session.queue(it) }
    }

    private fun getReferenceData(): ByteBuffer {
        if (cachedReference == null) {
            cachedReference = Cache.generateReferenceData()
        }
        val buffer = ByteBuffer.allocate(cachedReference!!.size * 4)
        buffer.put(255.toByte())
        buffer.putShort(255.toShort())
        buffer.put(0)
        buffer.putInt(cachedReference!!.size)
        var offset = 10
        for (element in cachedReference!!) {
            if (offset == 512) {
                buffer.put(255.toByte())
                offset = 1
            }
            buffer.put(element)
            offset++
        }
        buffer.flip()
        return buffer
    }

    companion object {
        var cachedReference: ByteArray? = null
    }
}
