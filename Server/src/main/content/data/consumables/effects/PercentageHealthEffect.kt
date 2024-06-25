package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

class PercentageHealthEffect(percentage: Int) : ConsumableEffect() {

    private val percentage = percentage * 0.01

    override fun activate(p: Player) {
        val effect = HealingEffect(getHealthEffectValue(p))
        effect.activate(p)
    }

    override fun getHealthEffectValue(player: Player): Int {
        return (player.getSkills().maximumLifepoints * percentage).toInt()
    }
}
