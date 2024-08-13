package content.data.consumables.effects

import core.api.impact
import core.game.consumable.ConsumableEffect
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player

/**
 * Poison effect.
 *
 * @param amount Represents the intensity of the poison effect.
 * @constructor Represents a PoisonEffect object with a specified amount of poison.
 */
class PoisonEffect(var amount: Int) : ConsumableEffect() {

    /**
     * Activates the poison effect on the player.
     *
     * @param player The player on whom the poison effect is activated.
     */
    override fun activate(player: Player) {
        impact(player, amount, ImpactHandler.HitsplatType.POISON)
    }

    /**
     * Retrieves the health effect value of the poison effect.
     *
     * @param player The player for whom the health effect value is calculated.
     * @return The negative amount representing the health effect of the poison.
     */
    override fun getHealthEffectValue(player: Player): Int {
        return -amount
    }

}
