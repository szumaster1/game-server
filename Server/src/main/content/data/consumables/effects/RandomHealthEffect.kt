package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.tools.RandomFunction

/**
 * Random health effect.
 *
 * @param a Lower bound for random health effect value.
 * @param b Upper bound for random health effect value.
 * @constructor Represents a RandomHealthEffect with given bounds.
 */
class RandomHealthEffect(val a: Int, val b: Int) : ConsumableEffect() {

    override fun activate(player: Player) {
        val effect: ConsumableEffect // Declares a variable to hold the health effect
        val healthEffectValue = getHealthEffectValue(player) // Retrieves the health effect value
        effect = if (healthEffectValue > 0) { // Checks if the health effect value is positive
            HealingEffect(healthEffectValue) // Creates a HealingEffect if health effect value is positive
        } else {
            DamageEffect(healthEffectValue.toDouble(), false) // Creates a DamageEffect if health effect value is non-positive
        }
        effect.activate(player) // Activates the calculated effect on the player
    }

    override fun getHealthEffectValue(player: Player): Int {
        return RandomFunction.random(a, b) // Generates a random health effect value within the specified bounds
    }

}
