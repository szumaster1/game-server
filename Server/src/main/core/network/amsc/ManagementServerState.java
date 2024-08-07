package core.network.amsc;

import core.game.node.entity.player.Player;
import core.game.world.repository.Repository;
import core.network.packet.PacketRepository;
import core.network.packet.context.ContactContext;
import core.network.packet.outgoing.ContactPackets;

/**
 * The enum Management server state.
 */
public enum ManagementServerState {

    /**
     * Not available management server state.
     */
    NOT_AVAILABLE(2), // Represents the state when the management server is not available

    /**
     * Connecting management server state.
     */
    CONNECTING(1), // Represents the state when the management server is connecting

    /**
     * Available management server state.
     */
    AVAILABLE(2); // Represents the state when the management server is available

    private final int value;

    private ManagementServerState(int value) {
        this.value = value;
    }

    /**
     * Set the state for all players.
     */
    public void set() {
        for (Player player : Repository.getPlayers()) {
            PacketRepository.send(ContactPackets.class, new ContactContext(player, ContactContext.UPDATE_STATE_TYPE));
        }
    }

    /**
     * Get the integer value of the state.
     *
     * @return the integer value representing the state
     */
    public int value() {
        return value;
    }
}
