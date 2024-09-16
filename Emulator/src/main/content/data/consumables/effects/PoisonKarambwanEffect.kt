package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Poison karambwan effect.
 */
class PoisonKarambwanEffect : ConsumableEffect() {

    // Check if player's lifepoints are greater than 5 before activating the effect
    override fun activate(player: Player) {
        if (player.getSkills().lifepoints > 5) {
            effect.activate(player)
        }
    }

    // Return the health effect value for the poison karambwan
    override fun getHealthEffectValue(player: Player): Int {
        return HEALING
    }

    companion object {
        private const val HEALING = -5
        private val effect = PoisonEffect(5)
    }
}
