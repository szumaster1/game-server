package core.net.packet.context;

import core.game.node.entity.player.Player;
import core.net.packet.Context;

/**
 * Varbit context.
 */
public class VarbitContext implements Context {

    /**
     * The Player.
     */
    Player player;
    /**
     * The Varbit id.
     */
    public int varbitId;
    /**
     * The Value.
     */
    public int value;

    /**
     * Instantiates a new Varbit context.
     *
     * @param player   the player
     * @param varbitId the varbit id
     * @param value    the value
     */
    public VarbitContext(Player player, int varbitId, int value) {
        this.player = player;
        this.varbitId = varbitId;
        this.value = value;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
