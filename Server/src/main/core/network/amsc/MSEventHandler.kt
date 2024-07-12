package core.network.amsc

import core.api.log
import core.network.IoEventHandler
import core.network.IoSession
import core.tools.Log
import java.io.IOException
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel
import java.util.concurrent.Executors

/**
 * Handles the management server events.
 * @author Emperor
 */
class MSEventHandler : IoEventHandler(Executors.newSingleThreadExecutor()) {

    @Throws(IOException::class)
    override fun connect(key: SelectionKey) {
        val ch = key.channel() as SocketChannel
        try {
            if (ch.finishConnect()) {
                key.interestOps(key.interestOps() xor SelectionKey.OP_CONNECT)
                key.interestOps(key.interestOps() or SelectionKey.OP_READ)
                var session: IoSession? = key.attachment() as IoSession
                key.attach(IoSession(key, service).also { session = it })
                session?.let { WorldCommunicator.register(it) }
                return
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        log(this.javaClass, Log.ERR, "Failed connecting to Management Server!")
        WorldCommunicator.terminate()
    }

    @Throws(IOException::class)
    override fun accept(key: SelectionKey, selector: Selector) {
        super.write(key)
    }

    @Throws(IOException::class)
    override fun read(key: SelectionKey) {
        super.read(key)
    }

    override fun write(key: SelectionKey) {
        super.write(key)
    }

    override fun disconnect(key: SelectionKey, t: Throwable) {
        super.disconnect(key, t)
        WorldCommunicator.terminate()
    }
}
