package content.data.consumables.effects

import core.api.registerTimer
import core.api.removeTimer
import core.api.spawnTimer
import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Add timer effect.
 *
 * @property identifier The unique identifier for the timer.
 * @property args       Additional arguments for the timer.
 * @constructor Add timer effect with specified identifier and arguments.
 */
class AddTimerEffect(val identifier: String, vararg val args: Any) : ConsumableEffect() {

    override fun activate(player: Player) {
        removeTimer(player, identifier) // Remove any existing timer with the same identifier.
        val timer = spawnTimer(identifier, *args) ?: return // Spawn a new timer with the provided identifier and arguments.
        registerTimer(player, timer) // Register the new timer for the player.
    }
}
