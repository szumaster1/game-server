package core.net.amsc

import core.game.world.repository.Repository
import core.net.packet.PacketRepository
import core.net.packet.context.ContactContext
import core.net.packet.outgoing.ContactPackets

/**
 * The management server state.
 * @author Emperor
 */
enum class ManagementServerState(val value: Int) {

    /**
     * Represents the state when the management server is not available
     */
    NOT_AVAILABLE(2),

    /**
     * Represents the state when the management server is connecting
     */
    CONNECTING(1),

    /**
     * Represents the state when the management server is available
     */
    AVAILABLE(2);

    /**
     * Called when the state gets set.
     */
    fun set() {
        for (player in Repository.players) {
            PacketRepository.send(ContactPackets::class.java, ContactContext(player, ContactContext.UPDATE_STATE_TYPE))
        }
    }
}
