package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.tools.RandomFunction

/**
 * Super kebab effect.
 */
class SuperKebabEffect : ConsumableEffect() {

    // Activates the super kebab effect for the player
    override fun activate(player: Player) {
        // Check if the random number is less than 5 to activate healing effect
        if (RandomFunction.nextInt(8) < 5) {
            healingEffect.activate(player)
        }
        // Check if the random number is less than 1 in 32 to activate a random skill effect
        if (RandomFunction.nextInt(32) < 1) {
            // Create a random skill effect and activate it for the player
            val effect = SkillEffect(RandomFunction.nextInt(Skills.NUM_SKILLS), -1.0, 0.0)
            effect.activate(player)
        }
    }

    // Get the health effect value for the player
    override fun getHealthEffectValue(player: Player): Int {
        // Calculate the health effect value based on a random chance
        return if (RandomFunction.nextInt(8) < 5) (3 + (player.getSkills().maximumLifepoints * 0.07)).toInt() else 0
    }

    companion object {
        // Define the healing effect for the super kebab
        private val healingEffect = MultiEffect(HealingEffect(3), PercentageHealthEffect(7))
    }

}
