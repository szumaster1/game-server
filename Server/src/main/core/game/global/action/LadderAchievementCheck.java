package core.game.global.action;

import core.game.node.entity.player.Player;

/**
 * The interface Ladder achievement check.
 */
interface LadderAchievementCheck {
    /**
     * Check achievement.
     *
     * @param player the player
     */
    default void checkAchievement(Player player) {
        return;
    }
}
