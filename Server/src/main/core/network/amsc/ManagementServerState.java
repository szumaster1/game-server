package core.network.amsc;

import core.game.node.entity.player.Player;
import core.game.world.repository.Repository;
import core.network.packet.PacketRepository;
import core.network.packet.context.ContactContext;
import core.network.packet.outgoing.ContactPackets;

public enum ManagementServerState {

    NOT_AVAILABLE(2),

    CONNECTING(1),

    AVAILABLE(2);

    private final int value;

    private ManagementServerState(int value) {
        this.value = value;
    }

    public void set() {
        for (Player player : Repository.getPlayers()) {
            PacketRepository.send(ContactPackets.class, new ContactContext(player, ContactContext.UPDATE_STATE_TYPE));
        }
    }

    public int value() {
        return value;
    }
}
