package core.net.packet.context;

import core.game.node.entity.player.Player;
import core.net.packet.Context;

/**
 * Default context.
 */
public class DefaultContext implements Context {
    private Player player;
    private Object[] objects;

    /**
     * Constructs a new Default context.
     *
     * @param player  the player
     * @param objects the objects
     */
    public DefaultContext(Player player, Object... objects) {
        this.player = player;
        this.objects = objects;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * Get objects object [ ].
     *
     * @return the object [ ]
     */
    public Object[] getObjects() {
        return objects;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets objects.
     *
     * @param objects the objects
     */
    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

}
