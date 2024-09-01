package core.network.amsc;

import core.game.node.entity.player.Player;
import core.game.world.repository.Repository;
import core.network.packet.PacketRepository;
import core.network.packet.context.ContactContext;
import core.network.packet.outgoing.ContactPackets;

/**
 * The management server state.
 * @author Emperor
 */
public enum ManagementServerState {

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
     * The value of this state.
     */
    private final int value;

    private ManagementServerState(int value) {
        this.value = value;
    }

    /**
     * Called when the state gets set.
     */
    public void set() {
        for (Player player : Repository.getPlayers()) {
            PacketRepository.send(ContactPackets.class, new ContactContext(player, ContactContext.UPDATE_STATE_TYPE));
        }
    }

    /**
     * Gets the state value.
     *
     * @return The value.
     */
    public int value() {
        return value;
    }
}
