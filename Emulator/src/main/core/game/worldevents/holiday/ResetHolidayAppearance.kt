package core.game.worldevents.holiday

import org.rs.consts.Sounds
import core.api.playAudio
import core.api.visualize
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.system.timer.RSTimer
import core.tools.minutesToTicks

/**
 * Reset holiday appearance.
 * @author Zerken
 */
class ResetHolidayAppearance : RSTimer(minutesToTicks(1), "reset-holiday-appearance") {
    override fun run(entity: Entity): Boolean {
        if (entity is Player) {
            entity.asPlayer().appearance.transformNPC(-1)
            playAudio(entity.asPlayer(), Sounds.WEAKEN_HIT_3010)
            visualize(entity, -1, 86)
        }
        entity.timers.removeTimer(this)
        return true
    }
}
