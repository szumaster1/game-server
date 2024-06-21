package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.utilities.RandomFunction

class RandomHealthEffect(private val a: Int, private val b: Int) : ConsumableEffect() {
    override fun activate(p: Player) {
        val effect: ConsumableEffect
        val healthEffectValue = getHealthEffectValue(p)
        effect = if (healthEffectValue > 0) {
            HealingEffect(healthEffectValue)
        } else {
            DamageEffect(healthEffectValue.toDouble(), false)
        }
        effect.activate(p)
    }

    override fun getHealthEffectValue(player: Player): Int {
        return RandomFunction.random(a, b)
    }
}
