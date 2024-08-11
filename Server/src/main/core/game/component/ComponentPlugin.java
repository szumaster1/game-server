package core.game.component;

import core.game.node.entity.player.Player;
import core.plugin.Plugin;

/**
 * Component plugin.
 */
public abstract class ComponentPlugin implements Plugin<Object> {

    /**
     * Handle method to process player input.
     *
     * @param player    the player object.
     * @param component the component being interacted with.
     * @param opcode    the operation code.
     * @param button    the button pressed.
     * @param slot      the slot involved.
     * @param itemId    the item ID.
     * @return true if the input is handled successfully, false otherwise.
     */
    public abstract boolean handle(final Player player, Component component, final int opcode, final int button, int slot, int itemId);

    /**
     * Open method to handle component opening.
     *
     * @param player    the player object.
     * @param component the component to be opened.
     */
    public void open(Player player, Component component) {
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

}