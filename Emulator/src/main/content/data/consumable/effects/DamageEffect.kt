package content.data.consumable.effects

import core.api.impact
import core.game.consumable.ConsumableEffect
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player

/**
 * Damage effect.
 *
 * @param amt The amount of damage to be inflicted.
 * @param isPercent Indicates if the damage is in percentage.
 * @constructor Damage effect constructor.
 */
class DamageEffect(val amt: Double, val isPercent: Boolean) : ConsumableEffect() {

    // Activates the damage effect on the player
    override fun activate(player: Player) {
        impact(player, -getHealthEffectValue(player), ImpactHandler.HitsplatType.NORMAL)
    }

    // Calculates the health effect value based on the amount and percentage
    override fun getHealthEffectValue(player: Player): Int {
        var amount = amt
        if (isPercent) {
            amount /= 100.0
            return -(amount * player.getSkills().lifepoints).toInt()
        }
        return -amt.toInt()
    }

}
