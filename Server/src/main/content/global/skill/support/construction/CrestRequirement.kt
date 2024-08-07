package content.global.skill.support.construction

import core.game.node.entity.player.Player

/**
 * Crest requirement.
 */
interface CrestRequirement {
    /**
     * Eligible
     *
     * @param player
     * @return
     */
    fun eligible(player: Player): Boolean {
        return true
    }
}