package core.network.packet.context;

import core.game.node.entity.player.Player;
import core.network.packet.Context;

/**
 * Varc update context.
 */
public class VarcUpdateContext implements Context {

    /**
     * The Player.
     */
    Player player;
    /**
     * The Varc id.
     */
    public int varcId;
    /**
     * The Value.
     */
    public int value;

    /**
     * Instantiates a new Varc update context.
     *
     * @param player the player
     * @param varcId the varc id
     * @param value  the value
     */
    public VarcUpdateContext(Player player, int varcId, int value) {
        this.player = player;
        this.varcId = varcId;
        this.value = value;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}

