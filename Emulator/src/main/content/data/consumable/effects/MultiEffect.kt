package content.data.consumable.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Multi effect.
 */
class MultiEffect(vararg effects: ConsumableEffect) : ConsumableEffect() {

    // Initializing an array to store multiple effects
    private val effects: Array<ConsumableEffect> = effects as Array<ConsumableEffect>

    // Activating each effect in the array for the player
    override fun activate(player: Player) {
        for (e in effects) {
            e.activate(player)
        }
    }

    // Calculating the total health effect value by summing up individual effects
    override fun getHealthEffectValue(player: Player): Int {
        var healing = 0
        for (effect in effects) {
            healing += effect.getHealthEffectValue(player)
        }
        return healing
    }
}
