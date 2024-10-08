package core.game.system.command;

import core.game.node.entity.player.Player;
import core.game.node.entity.player.info.Rights;
import core.game.world.GameWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command set.
 * @author Vexia
 */
public enum CommandSet {
    /**
     * Player command set.
     */
    PLAYER(),
    /**
     * The Moderator.
     */
    MODERATOR() {
        @Override
        public boolean validate(Player player) {
            return player.getDetails().getRights().ordinal() > 0;
        }
    },
    /**
     * The Administrator.
     */
    ADMINISTRATOR() {
        @Override
        public boolean validate(Player player) {
            return player.getDetails().getRights().equals(Rights.ADMINISTRATOR);
        }
    },
    /**
     * The Developer.
     */
    DEVELOPER() {
        @Override
        public boolean validate(Player player) {
            return player.getDetails().getRights().equals(Rights.ADMINISTRATOR);
        }
    },
    /**
     * The Beta.
     */
    BETA() {
        @Override
        public boolean validate(Player player) {
            return GameWorld.getSettings().isBeta() || ADMINISTRATOR.validate(player) || GameWorld.getSettings().isDevMode();
        }
    };

    private final List<CommandPlugin> plugins = new ArrayList<>(20);

    private CommandSet() {
    }

    /**
     * Checks if the player can use this set.
     *
     * @param player the player.
     * @return the boolean
     */
    public boolean validate(final Player player) {
        return true;
    }

    /**
     * Interprets and incoming command by dispatching it to it's plugins.
     *
     * @param player    the player.
     * @param name      the name.
     * @param arguments the arguments.
     * @return `true` if the command was interpreted.
     */
    public boolean interpret(final Player player, final String name, final String... arguments) {
        if (player == null) {
            return false;
        }
        if (!validate(player)) {
            return false;
        }
        if (player.getZoneMonitor().parseCommand(player, name, arguments)) {
            return true;
        }
        for (int i = 0; i < plugins.size(); i++) {
            CommandPlugin plugin = plugins.get(i);
            if (!plugin.validate(player)) {
                continue;
            }
            if (plugin.parse(player, name, arguments)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of plugins of this command set.
     *
     * @return the plugins of this set.
     */
    public List<CommandPlugin> getPlugins() {
        return plugins;
    }
}
