package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

/**
 * Keg of beer effect.
 */
class KegOfBeerEffect : ConsumableEffect() {

    // Activates the Keg of Beer effect on the player
    override fun activate(player: Player) {
        // Define the effect of the Keg of Beer, including healing, strength, and attack changes
        val effect = MultiEffect(
            HealingEffect(15), // Healing the player by 15 points
            SkillEffect(Skills.STRENGTH, 10.0, 0.0), // Increasing player's strength by 10 points
            SkillEffect(Skills.ATTACK, -40.0, 0.0) // Decreasing player's attack by 40 points
        )
        effect.activate(player) // Activate the defined effect on the player
    }

}
