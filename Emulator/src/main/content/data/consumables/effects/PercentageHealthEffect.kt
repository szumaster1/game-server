package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Percentage health effect.
 */
class PercentageHealthEffect(percentage: Int) : ConsumableEffect() {

    // Calculate the actual percentage value
    private val percentage = percentage * 0.01

    // Activate the percentage health effect
    override fun activate(player: Player) {
        // Create a HealingEffect instance with the calculated health effect value
        val effect = HealingEffect(getHealthEffectValue(player))
        effect.activate(player)
    }

    // Get the health effect value based on the player's maximum lifepoints
    override fun getHealthEffectValue(player: Player): Int {
        return (player.getSkills().maximumLifepoints * percentage).toInt()
    }
}
