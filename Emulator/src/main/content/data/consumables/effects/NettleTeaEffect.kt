package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Nettle tea effect.
 */
class NettleTeaEffect : ConsumableEffect() {

    override fun activate(player: Player) {
        val effect = if (player.getSkills().lifepoints < player.getSkills().maximumLifepoints) MultiEffect(
            HealingEffect(3),
            EnergyEffect(5)
        ) else HealingEffect(3)
        effect.activate(player)
    }

    override fun getHealthEffectValue(player: Player): Int {
        return HEALING
    }

    companion object {
        private const val HEALING = 3
    }
}
