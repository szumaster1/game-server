package content.data.consumable.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Ugthanki kebab effect.
 */
class UgthankiKebabEffect : ConsumableEffect() {

    // Activates the Ugthanki Kebab effect for the player
    override fun activate(player: Player) {
        // Check if player's lifepoints are less than maximum lifepoints
        if (player.getSkills().lifepoints < player.getSkills().maximumLifepoints) {
            player.sendChat("Yum!")
        }
        effect.activate(player) // Activate the effect
    }

    // Returns the health effect value for the player
    override fun getHealthEffectValue(player: Player): Int {
        return HEALING
    }

    companion object {
        private const val HEALING = 19 // Constant value for healing
        private val effect = HealingEffect(HEALING) // Initialize HealingEffect with HEALING value
    }

}
