package core.plugin;

import core.game.node.entity.player.Player;

/**
 * Represents a plugin.
 * @author Emperor
 * @param <T> The argument type.
 */
public interface Plugin<T> {

    /**
     * Creates a new instance of the plugin.
     *
     * @param arg the argument to initialize the plugin.
     * @return the new instance of the plugin.
     * @throws Throwable if an error occurs during instantiation.
     */
    public Plugin<T> newInstance(T arg) throws Throwable;

    /**
     * Fires an event based on the provided identifier and arguments.
     *
     * @param identifier the identifier of the event.
     * @param args       the arguments for the event.
     * @return the result of the event.
     */
    Object fireEvent(String identifier, Object... args);

    /**
     * Handles the selection callback for a specific skill and player.
     *
     * @param skill  the skill associated with the callback.
     * @param player the player triggering the callback.
     */
    public default void handleSelectionCallback(int skill, Player player) {

    }
    ;

}
