package content.data.consumable.effects

import core.api.removeTimer
import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Remove timer effect.
 *
 * @param identifier Unique identifier for the timer to be removed.
 * @constructor Represents the RemoveTimerEffect with a specific identifier.
 */
class RemoveTimerEffect(val identifier: String) : ConsumableEffect() {

    /**
     * Activates the RemoveTimerEffect by removing a timer associated with the provided identifier.
     *
     * @param player The player for whom the timer needs to be removed.
     */
    override fun activate(player: Player) {
        removeTimer(player, identifier) // Removes the timer for the specified player using the provided identifier
    }

}
