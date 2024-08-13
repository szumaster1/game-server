package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.tools.RandomFunction

/**
 * Random energy effect.
 *
 * @property a Represents the lower bound of the random energy effect range.
 * @property b Represents the upper bound of the random energy effect range.
 * @constructor Represents a Random energy effect with given range.
 */
class RandomEnergyEffect(val a: Int, val b: Int) : ConsumableEffect() {

    override fun activate(player: Player) {
        // Create a new EnergyEffect with a random value between a and b
        val effect = EnergyEffect(RandomFunction.random(a, b))
        effect.activate(player)
    }

}
