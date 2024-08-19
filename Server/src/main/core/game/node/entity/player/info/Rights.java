package core.game.node.entity.player.info;

import core.ServerConstants;
import core.game.node.entity.player.Player;

/**
 * Represent the rights of a player.
 * @author Vexia
 */
public enum Rights {
    /**
     * Regular player rights, indicating standard access without special privileges.
     */
    REGULAR_PLAYER,

    /**
     * Player moderator rights, granting additional powers to manage player interactions.
     */
    PLAYER_MODERATOR,

    /**
     * The Administrator, who has the highest level of control and visibility.
     */
    ADMINISTRATOR() {
        @Override
        public boolean isVisible(Player player) {
            // Checks if the player's visible rank matches the ADMINISTRATOR rank.
            return player.getAttribute("visible_rank", ADMINISTRATOR) == ADMINISTRATOR;
        }
    };

    /**
     * Gets the chat icon based on the player's rights.
     *
     * @param player the player whose chat icon is to be retrieved
     * @return the chat icon associated with the player's rights
     */
    public static int getChatIcon(Player player) {
        // Retrieve the player's current rights based on their visible rank.
        Rights c = player.getAttribute("visible_rank", player.getDetails().getRights());

        // If the player has rights other than REGULAR_PLAYER, return the corresponding icon.
        if (c != Rights.REGULAR_PLAYER && c != null) {
            return c.toInteger();
        }

        // Check if Ironman icons are enabled and if the player is an Ironman.
        if (ServerConstants.IRONMAN_ICONS) {
            if (player.getIronmanManager().isIronman()) {
                // Return the icon associated with the player's Ironman mode.
                return player.getIronmanManager().getMode().icon;
            }
        }

        // Default return value if no specific icon is found.
        return 0;
    }

    /**
     * Checks if the player has a hidden rank compared to their actual rights.
     *
     * @param player the player to check for hidden rank
     * @return true if the player's visible rank is different from their actual rights, false otherwise
     */
    public static boolean isHidden(final Player player) {
        // Determine if the player's visible rank differs from their actual rights.
        return player.getAttribute("visible_rank", player.getDetails().getRights()) != player.getDetails().getRights();
    }

    /**
     * Gets the ordinal value of the rights, which represents its position in the enum.
     *
     * @return the ordinal value of the rights
     */
    public final int toInteger() {
        // Return the ordinal value of the current enum instance.
        return ordinal();
    }

    /**
     * Method used to get the Rights enum based on the provided id.
     *
     * @param id the id representing the rights
     * @return the corresponding Rights enum instance
     */
    public static Rights forId(int id) {
        // Ensure the id is non-negative; default to 0 if it is.
        if (id < 0) {
            id = 0;
        }
        // Return the Rights enum instance corresponding to the id.
        return values()[id];
    }

    /**
     * Checks if the player's rank is visible.
     *
     * @param username the username of the player to check
     * @return true if the player's rank is visible, false otherwise
     */
    public boolean isVisible(Player username) {
        // By default, all players' ranks are considered visible.
        return true;
    }
}