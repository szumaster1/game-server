package core.game.global.action

import core.game.node.entity.player.Player

/**
 * Interface for checking ladder achievements.
 */
internal interface LadderAchievementCheck {
    /**
     * Method to check player's achievement.
     *
     * @param player the player whose achievement needs to be checked.
     */
    fun checkAchievement(player: Player) {
        return
    }
}
