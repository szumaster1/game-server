package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.utilities.RandomFunction

class RandomPrayerEffect(val a: Int, val b: Int) : ConsumableEffect() {

    override fun activate(p: Player) {
        val effect = PrayerEffect(RandomFunction.random(a, b).toDouble(), 0.0)
        effect.activate(p)
    }

}
