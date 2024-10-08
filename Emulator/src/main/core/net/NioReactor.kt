package core.net

import core.net.amsc.MSEventHandler
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Handles (NIO-based) networking events using the reactor pattern.
 * @author Emperor
 */
class NioReactor private constructor(private val eventHandler: IoEventHandler) : Runnable {

    /**
     * The executor service.
     */
    private val service: ExecutorService = Executors.newSingleThreadScheduledExecutor()

    /**
     * The I/O event handling instance.
     */
    private var channel: ServerSocketConnection? = null

    /**
     * If the reactor is running.
     */
    private var running: Boolean = false

    companion object {
        /**
         * Creates and configures a new {@code NioReactor} with a pool size of 1.
         *
         * @param port The port.
         * @return The {@code NioReactor} {@code Object}.
         * @throws IOException When an I/O exception occurs.
         */
        @Throws(IOException::class)
        fun configure(port: Int): NioReactor {
            return configure(port, 1)
        }

        /**
         * Creates and configures a new {@code NioReactor}.
         *
         * @param port The port.
         * @param poolSize The amount of threads in the thread pool.
         * @return The {@code NioReactor} {@code Object}.
         * @throws IOException When an I/O exception occurs.
         */
        @Throws(IOException::class)
        fun configure(port: Int, poolSize: Int): NioReactor {
            val reactor = NioReactor(IoEventHandler(Executors.newFixedThreadPool(poolSize)))
            val channel = ServerSocketChannel.open()
            val selector = Selector.open()
            channel.bind(InetSocketAddress(port))
            channel.configureBlocking(false)
            channel.register(selector, SelectionKey.OP_ACCEPT)
            reactor.channel = ServerSocketConnection(selector, channel)
            return reactor
        }

        /**
         * Starts a NIO reactor used for client connections.
         *
         * @param address The IP-address to connect to.
         * @param port The port used.
         * @return The NIO reactor object.
         * @throws IOException When an exception occurs.
         */
        @Throws(IOException::class)
        fun connect(address: String, port: Int): NioReactor {
            val reactor = NioReactor(MSEventHandler())
            val selector = Selector.open()
            val channel = SocketChannel.open()
            channel.configureBlocking(false)
            channel.socket().keepAlive = true
            channel.socket().tcpNoDelay = true
            channel.connect(InetSocketAddress(address, port))
            channel.register(selector, SelectionKey.OP_CONNECT)
            reactor.channel = ServerSocketConnection(selector, channel)
            return reactor
        }
    }

    /**
     * Starts the reactor.
     */
    fun start() {
        running = true
        service.execute(this)
    }

    override fun run() {
        Thread.currentThread().name = "NioReactor"
        while (running) {
            try {
                channel?.selector?.select()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val iterator = channel?.selector?.selectedKeys()?.iterator()
            while (iterator?.hasNext() == true) {
                val key = iterator.next()
                iterator.remove()
                try {
                    if (!key.isValid || !key.channel().isOpen) {
                        key.cancel()
                        continue
                    }
                    when {
                        key.isConnectable -> eventHandler.connect(key)
                        key.isAcceptable -> eventHandler.accept(key, channel?.selector)
                        key.isReadable -> eventHandler.read(key)
                        key.isWritable -> eventHandler.write(key)
                    }
                } catch (t: Throwable) {
                    eventHandler.disconnect(key, t)
                }
            }
        }
    }

    /**
     * Terminates the NioReactor.
     */
    fun terminate() {
        running = false
    }
}
