package core.network.event

import core.cache.misc.buffer.ByteBufferUtils;
import core.game.world.GameWorld;
import core.network.Constants;
import core.network.IoSession;
import core.network.IoWriteEvent;

import java.nio.ByteBuffer;

class RegistryWriteEvent(session: IoSession, context: Any) : IoWriteEvent(session, context) {

    companion object {
        private const val CHECK = "12x4578f5g45hrdjiofed59898"
    }

    override fun write(session: IoSession, context: Any) {
        val buffer = ByteBuffer.allocate(128)
        with(GameWorld.settings) {
            buffer.put(this!!.worldId.toByte())
            buffer.putInt(Constants.REVISION)
            buffer.put(countryIndex.toByte())
            buffer.put(if (isMembers) 1 else 0)
            buffer.put(if (isPvp) 1 else 0)
            buffer.put(if (isQuickChat) 1 else 0)
            buffer.put(if (isLootshare) 1 else 0)
            ByteBufferUtils.putString(activity, buffer)
        }
        buffer.put(CHECK.toByteArray())
        session.queue(buffer.flip() as ByteBuffer)
    }
}