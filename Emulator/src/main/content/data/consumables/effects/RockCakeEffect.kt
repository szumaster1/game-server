package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Rock cake effect.
 */
class RockCakeEffect : ConsumableEffect() {

    // Activates the Rock Cake effect for the player if their lifepoints are greater than 1
    override fun activate(player: Player) {
        if (player.getSkills().lifepoints > 1) {
            effect.activate(player) // Activates the effect
        }
    }

    // Returns the health effect value of the Rock Cake effect for the player
    override fun getHealthEffectValue(player: Player): Int {
        return (player.getSkills().lifepoints * -0.1).toInt()
    }

    companion object {
        private val effect = DamageEffect(10.0, true) // Represents the DamageEffect with a value of 10.0 and true
    }

}
