package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.tools.RandomFunction

/**
 * Random prayer effect.
 *
 * @property a Represents the lower bound for the random value generation.
 * @property b Represents the upper bound for the random value generation.
 * @constructor Initializes a RandomPrayerEffect with given range.
 */
class RandomPrayerEffect(val a: Int, val b: Int) : ConsumableEffect() {

    override fun activate(player: Player) {
        // Generate a random value within the range [a, b] and create a PrayerEffect with it.
        val effect = PrayerEffect(RandomFunction.random(a, b).toDouble(), 0.0)
        effect.activate(player)
    }

}
