package core.game.node.entity.player.info;

import core.ServerConstants;
import core.game.node.entity.player.Player;

/**
 * @author 'Vexia
 */
public enum Rights {
    REGULAR_PLAYER,
    PLAYER_MODERATOR,
    ADMINISTRATOR() {
        @Override
        public boolean isVisible(Player player) {
            return player.getAttribute("visible_rank", ADMINISTRATOR) == ADMINISTRATOR;
        }
    };

    /*
     * Gets the chat icon.
     */
    public static int getChatIcon(Player player) {
        Rights c = player.getAttribute("visible_rank", player.getDetails().getRights());
        if (c != Rights.REGULAR_PLAYER && c != null) {
            return c.toInteger();
        }
        if (ServerConstants.IRONMAN_ICONS) {
            if (player.getIronmanManager().isIronman()) {
                return player.getIronmanManager().getMode().getIcon();
            }
        }
        return 0;
    }

    /*
     * Checks if the player has a hidden rank.
     */
    public static boolean isHidden(final Player player) {
        return player.getAttribute("visible_rank", player.getDetails().getRights()) != player.getDetails().getRights();
    }

    /*
     * Gets the ordinal of the rights.
     */
    public final int toInteger() {
        return ordinal();
    }

    /*
     * Method used to get the credentials based off the id.
     */
    public static Rights forId(int id) {
        if (id < 0) {
            id = 0;
        }
        return values()[id];
    }

    /*
     * Checks if the player's rank is visible.
     */
    public boolean isVisible(Player username) {
        return true;
    }
}
