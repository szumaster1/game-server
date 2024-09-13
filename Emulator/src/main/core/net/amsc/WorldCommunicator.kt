package core.net.amsc

import core.game.node.entity.player.info.login.LoginParser
import core.game.world.GameWorld
import core.net.EventProducer
import core.net.IoSession
import core.net.NioReactor
import core.net.producer.MSHSEventProducer
import java.io.IOException
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.concurrent.ConcurrentHashMap

/**
 * Handles world communication.
 * @author Emperor
 */
object WorldCommunicator {
    /**
     * The handshake events producer.
     */
    private val HANDSHAKE_PRODUCER: EventProducer = MSHSEventProducer()

    /**
     * The current state.
     */
    private var state: ManagementServerState = ManagementServerState.CONNECTING

    /**
     * The I/O session.
     */
    private lateinit var session: IoSession

    /**
     * The world information.
     */
    private val WORLDS = Array(10) { WorldStatistics(it + 1) }

    /**
     * The current login attempts.
     */
    private val loginAttempts = ConcurrentHashMap<String, LoginParser>()

    /**
     * The NIO reactor.
     */
    private lateinit var reactor: NioReactor

    /**
     * Registers a new world.
     *
     * @param session The session.
     */
    fun register(session: IoSession) {
        this.session = session
        session.producer = HANDSHAKE_PRODUCER
        session.write(true)
        WORLDS[GameWorld.settings!!.worldId - 1] = WorldStatistics(GameWorld.settings!!.worldId)
        session.`object` = (WORLDS[GameWorld.settings!!.worldId - 1])
    }

    /**
     * Attempts to connect to the management server.
     */
    fun connect() {
        try {
            setState(ManagementServerState.CONNECTING)
            reactor = NioReactor.connect(GameWorld.settings!!.msAddress, 5555)
            reactor.start()
        } catch (e: Throwable) {
            e.printStackTrace()
            terminate()
        }
    }

    /**
     * Checks if the Management server is locally hosted.
     *
     * @return {@code True} if so.
     * @throws IOException When an I/O exception occurs.
     */
    @Throws(IOException::class)
    private fun isLocallyHosted(): Boolean {
        val address = InetAddress.getByName(GameWorld.settings!!.msAddress)
        if (address.isAnyLocalAddress || address.isLoopbackAddress) {
            return true
        }
        return NetworkInterface.getByInetAddress(address) != null
    }

    /**
     * Terminates the world communicator.
     */
    fun terminate() {
        setState(ManagementServerState.NOT_AVAILABLE)
        reactor.terminate()
    }

    /**
     * Gets and removes the login attempt for the given username.
     *
     * @param username The username.
     * @return The login attempt.
     */
    fun finishLoginAttempt(username: String): LoginParser? {
        return loginAttempts.remove(username)
    }

    /**
     * Gets the local world.
     *
     * @return The world statistics of this world server.
     */
    fun getLocalWorld(): WorldStatistics {
        return WORLDS[GameWorld.settings!!.worldId - 1]
    }

    /**
     * Gets the id of the world the player is connected to.
     *
     * @param playerName The player's name.
     * @return The world id, or -1 if the player wasn't connected.
     */
    fun getWorld(playerName: String): Int {
        for (i in WORLDS.indices) {
            if (WORLDS[i].players.contains(playerName)) {
                return i
            }
        }
        return -1
    }

    /**
     * Gets the world statistics for the given index.
     *
     * @param id The world id.
     * @return The world statistics.
     */
    fun getWorld(id: Int): WorldStatistics {
        return WORLDS[id - 1]
    }

    /**
     * Gets the session.
     *
     * @return the session
     */
    fun getSession(): IoSession {
        return session
    }

    /**
     * Checks if this world is connected to the Management server.
     *
     * @return {@code True} if so.
     */
    @JvmStatic
    fun isEnabled(): Boolean {
        return state == ManagementServerState.AVAILABLE
    }

    /**
     * Gets the login attempts mapping.
     *
     * @return The login attempts mapping.
     */
    fun getLoginAttempts(): Map<String, LoginParser> {
        return loginAttempts
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    fun getState(): ManagementServerState {
        return state
    }

    /**
     * Sets the state.
     *
     * @param state the state to set.
     */
    fun setState(state: ManagementServerState) {
        if (WorldCommunicator.state != state) {
            WorldCommunicator.state = state
            state.set()
        }
    }

    /**
     * Gets the reactor.
     *
     * @return the reactor
     */
    fun getReactor(): NioReactor {
        return reactor
    }

    /**
     * Sets the reactor.
     *
     * @param reactor the reactor to set.
     */
    fun setReactor(reactor: NioReactor) {
        WorldCommunicator.reactor = reactor
    }
}
