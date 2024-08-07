package core.game.global.action

import core.game.node.entity.player.Player

/**
 * Ladder achievement check
 *
 * @constructor Ladder achievement check
 */
internal interface LadderAchievementCheck {
    /**
     * Check achievement
     *
     * @param player
     */
    fun checkAchievement(player: Player) {
        return
    }
}
