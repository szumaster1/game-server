package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * The Ugthanki kebab effect.
 */
class UgthankiKebabEffect : ConsumableEffect() {
    override fun activate(p: Player) {
        if (p.getSkills().lifepoints < p.getSkills().maximumLifepoints) {
            p.sendChat("Yum!")
        }
        effect.activate(p)
    }

    override fun getHealthEffectValue(player: Player): Int {
        return HEALING
    }

    companion object {
        private const val HEALING = 19
        private val effect = HealingEffect(HEALING)
    }
}
