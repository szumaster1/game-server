package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.tools.RandomFunction

/**
 * Smelling ugthanki kebab effect.
 */
class SmellingUgthankiKebabEffect : ConsumableEffect() {

    // Activates the effect of smelling Ugthanki kebab on the player.
    override fun activate(player: Player) {
        // Check if the random number is less than the defined percentage.
        if (RandomFunction.nextInt(100) < PERCENTAGE) {
            effect.activate(player) // Activate the effect.
        }
    }

    // Retrieves the health effect value of smelling Ugthanki kebab for the player.
    override fun getHealthEffectValue(player: Player): Int {
        // Check if the random number is less than the defined percentage.
        return if (RandomFunction.nextInt(100) < PERCENTAGE) HEALING else 0
    }

    companion object {
        private const val PERCENTAGE = 10 // Percentage threshold for the effect to occur.
        private const val HEALING = 9 // Healing value for the effect.
        private val effect = HealingEffect(HEALING) // Initialize the HealingEffect with the healing value.
    }

}
