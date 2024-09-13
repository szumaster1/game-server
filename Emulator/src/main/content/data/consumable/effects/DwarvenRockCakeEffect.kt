package content.data.consumable.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Dwarven rock cake effect.
 */
class DwarvenRockCakeEffect : ConsumableEffect() {

    // Activates the Dwarven Rock Cake effect if the player's lifepoints are greater than 2.
    override fun activate(player: Player) {
        if (player.getSkills().lifepoints > 2) {
            effect.activate(player)
        }
    }

    // Returns the health effect value based on the player's lifepoints.
    // If lifepoints are greater than 2, returns -1; otherwise, returns 0.
    override fun getHealthEffectValue(player: Player): Int {
        return if (player.getSkills().lifepoints > 2) -1 else 0
    }

    companion object {
        private val effect = DamageEffect(1.0, false) // Represents the DamageEffect with a multiplier of 1.0 and false flag.
    }

}
