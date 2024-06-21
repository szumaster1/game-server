package content.global.skill.support.construction

import core.game.node.entity.player.Player

interface CrestRequirement {
    fun eligible(player: Player): Boolean {
        return true
    }
}