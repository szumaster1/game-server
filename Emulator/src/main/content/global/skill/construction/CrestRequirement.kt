package content.global.skill.construction

import core.game.node.entity.player.Player

/**
 * Crest requirement.
 */
interface CrestRequirement {
    /**
     * Checks if the player is eligible for the crest.
     *
     * @param player The player whose eligibility is being checked.
     * @return True if the player is eligible, false otherwise.
     */
    fun eligible(player: Player) = true
}