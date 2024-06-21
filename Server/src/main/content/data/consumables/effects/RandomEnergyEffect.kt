package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.utilities.RandomFunction

class RandomEnergyEffect(private val a: Int, private val b: Int) : ConsumableEffect() {
    override fun activate(p: Player) {
        val effect = EnergyEffect(RandomFunction.random(a, b))
        effect.activate(p)
    }
}
